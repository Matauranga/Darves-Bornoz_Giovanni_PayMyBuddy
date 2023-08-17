package com.giovanniOpenclassrooms.paymybuddy.business;

import com.giovanniOpenclassrooms.paymybuddy.DTO.TransactionDTO;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.NegativeBalanceAccount;
import com.giovanniOpenclassrooms.paymybuddy.exceptions.NotFoundException;
import com.giovanniOpenclassrooms.paymybuddy.model.Person;
import com.giovanniOpenclassrooms.paymybuddy.model.Transaction;
import com.giovanniOpenclassrooms.paymybuddy.repository.PersonRepository;
import com.giovanniOpenclassrooms.paymybuddy.repository.TransactionRepository;
import com.giovanniOpenclassrooms.paymybuddy.utils.PersonFaker;
import com.giovanniOpenclassrooms.paymybuddy.utils.TransactionFaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TransactionServiceImplTest {
    @InjectMocks
    TransactionServiceImpl transactionServiceImpl;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    PersonRepository personRepository;

    @DisplayName("Try to get all transactions")
    @Test
    void getAllTransactions() {
        //Given an initial list of transactions
        Transaction transaction1 = TransactionFaker.generate();
        Transaction transaction2 = TransactionFaker.generate();

        List<Transaction> transactions = List.of(transaction1, transaction2);

        //When we try to get all transactions
        when(transactionRepository.findAll()).thenReturn(transactions);
        List<Transaction> response = transactionServiceImpl.getAllTransactions();

        //Then we verify if we get persons
        assertThat(response).isNotEmpty().containsAll(transactions);
        verify(transactionRepository, times(1)).findAll();
    }

    @DisplayName("Try to get a transaction by its ID")
    @Test
    void getTransactionById() {
        //Given an initial transaction with an id
        Transaction transaction = TransactionFaker.generate();
        UUID uuidTransaction = transaction.getTransactionId();

        //When we try to get this transaction
        when(transactionRepository.findById(any())).thenReturn(Optional.of(transaction));
        Optional<Transaction> response = transactionServiceImpl.getTransactionById(uuidTransaction);

        //Then we verify if we have the good transaction and if all works correctly
        assertThat(response).isNotEmpty().contains(transaction);
        verify(transactionRepository, times(1)).findById(any());
    }

    @DisplayName("Try to get all transactions for a person")
    @Test
    void getTransactionsByPerson() { //TODO : changer transaction2 les 2 UUID si on récupère tout, sinon laisser comme ça
        //Given initial list of transactions and the corresponding persons
        Person person1 = PersonFaker.generate();
        UUID uuidPerson1 = person1.getPersonId();

        UUID uuidPerson2 = UUID.randomUUID();
        UUID uuidPerson3 = UUID.randomUUID();

        Transaction transaction1 = new Transaction(uuidPerson1, uuidPerson2, null, null);
        Transaction transaction2 = new Transaction(uuidPerson1, uuidPerson3, null, null);
        Transaction transaction3 = new Transaction(uuidPerson2, uuidPerson3, null, null);

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        //When we try to get all transaction of person1
        when(transactionRepository.findAll()).thenReturn(transactions);
        List<Transaction> response = transactionServiceImpl.getTransactionsByPerson(person1);

        //Then we verify if we have the good transactions and if all works correctly
        assertThat(response).isNotEmpty().containsExactly(transaction1, transaction2);
        verify(transactionRepository, times(1)).findAll();
    }

    @DisplayName("Try to proceed to an electronic transfer")
    @Test
    void transferElectronicMoney() {
        //Given an initial money transfer
        Person debtor = PersonFaker.generate();
        UUID debtorId = debtor.getPersonId();
        debtor.setAmountBalance(new BigDecimal("50.0"));

        Person creditor = PersonFaker.generate();
        UUID creditorId = creditor.getPersonId();
        creditor.setAmountBalance(new BigDecimal("50.0"));

        TransactionDTO transactionDTO = new TransactionDTO(debtorId, creditorId, new BigDecimal("5.0"), "Yo");

        //When we make the transfer
        when(personRepository.findById(debtorId)).thenReturn(Optional.of(debtor));
        when(personRepository.findById(creditorId)).thenReturn(Optional.of(creditor));
        transactionServiceImpl.transferElectronicMoney(transactionDTO);

        //Then we verify if this have works correctly
        verify(personRepository, times(2)).findById(any());
        verify(personRepository, times(1)).saveAll(any());
        verify(transactionRepository, times(1)).save(any());
        assertThat(creditor.getAmountBalance()).isEqualTo(new BigDecimal("55.0"));
    }

    @DisplayName("Try to proceed to an electronic transfer without enough money")
    @Test
    void transferElectronicMoneyFailedNotMoney() {
        //Given an initial money transfer with a debtor without money
        Person debtor = PersonFaker.generate();
        UUID debtorId = debtor.getPersonId();
        debtor.setAmountBalance(new BigDecimal("0.0"));


        Person creditor = PersonFaker.generate();
        UUID creditorId = creditor.getPersonId();
        creditor.setAmountBalance(new BigDecimal("50.0"));

        TransactionDTO transactionDTO = new TransactionDTO(debtorId, creditorId, new BigDecimal("5.0"), "Yo");

        //When we try to make the transfer
        when(personRepository.findById(debtorId)).thenReturn(Optional.of(debtor));
        when(personRepository.findById(creditorId)).thenReturn(Optional.of(creditor));

        assertThrows(NegativeBalanceAccount.class, () -> {
            transactionServiceImpl.transferElectronicMoney(transactionDTO);
        });

        //Then we verify if the transfer don't work
        verify(personRepository, times(1)).findById(any());
        verify(personRepository, times(0)).saveAll(any());
        verify(transactionRepository, times(0)).save(any());
        assertThat(debtor.getAmountBalance()).isEqualTo(new BigDecimal("0.0"));
        assertThat(creditor.getAmountBalance()).isEqualTo(new BigDecimal("50.0"));
    }


    @DisplayName("Try to proceed to an electronic transfer but don't find a person")//integration
    @Test
    void transferElectronicMoneyFailedPersonNotFound() {
        //Given an initial money transfer with a debtor without money
        UUID debtorId = UUID.randomUUID();
        UUID creditorId = UUID.randomUUID();

        TransactionDTO transactionDTO = new TransactionDTO(debtorId, creditorId, new BigDecimal("5.0"), "Yo");

        //When we try to make the transfer
        when(personRepository.findById(debtorId)).thenReturn(Optional.empty());
        when(personRepository.findById(creditorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            transactionServiceImpl.transferElectronicMoney(transactionDTO);
        });

        //Then we verify if the transfer don't work
        verify(personRepository, times(1)).findById(any());
        verify(personRepository, times(0)).saveAll(any());
        verify(transactionRepository, times(0)).save(any());
    }

}
