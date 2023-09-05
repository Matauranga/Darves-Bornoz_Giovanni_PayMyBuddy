insert into PUBLIC.PERSON (AMOUNT_BALANCE, BIRTHDATE, ID, EMAIL, FIRSTNAME, LASTNAME, PASSWORD)
values  (40.99, '1977-10-19', 'c076de71-b095-4e98-a58d-3e0ec3199daf', 'jedi@mail.fr', 'Luke', 'Skywalker', '$2a$10$ZSC9aOm3As6cyO4EvhBLwO4CkUv6QSOONxF4hJ0oYuSkbBV8ldoW.'),
        (294.97, '2023-07-31', '1c02d056-06c6-47c4-89d6-0122032dfb33', 'g@mail.fr', 'Goo', 'Gle', '$2a$10$oXfEHt.q8PBFXzuaY1t2/.wmLHSPi8ON8Cb8TDKAMo2/IsbfCGEnG'),
        (118.99, '2023-07-31', 'a109ae40-e1d9-42c2-bdd5-a7585ad836b0', 'baba@mail.fr', 'Baba', 'Au-Rhum', '$2a$10$3GusYEcweJL1DdndZgPFkOAP7ItDMYPug57mxd6hWuDf/Nxn9OTta'),
        (4.70, '1956-05-12', '30ecf9f8-cb08-45e4-af77-317d24171964', 'homer@mail.fr', 'Homer', 'Simpson', '$2a$10$D2dpACkbY1py4kU/ZwmlIe.K6ZwcK9TGi8drYuI/vy6JJJZId0IIS'),
        (4.70, '1956-07-10', 'e66e2c55-abb9-4b4f-bdc8-37da37a3542d', 'tesla@mail.fr', 'Nikola', 'Tesla', '$2a$10$ArlqWhs750QisMdF053b1.klfJqpxLzuVQh7Fsi5mylkznTFBVuwK');


insert into PUBLIC.PERSON_CONNECTIONS_LIST (CONNECTIONS_LIST_ID, PERSON_ID)
values  ('c076de71-b095-4e98-a58d-3e0ec3199daf', '1c02d056-06c6-47c4-89d6-0122032dfb33'),
        ('a109ae40-e1d9-42c2-bdd5-a7585ad836b0', '1c02d056-06c6-47c4-89d6-0122032dfb33'),
        ('30ecf9f8-cb08-45e4-af77-317d24171964', '1c02d056-06c6-47c4-89d6-0122032dfb33'),
        ('c076de71-b095-4e98-a58d-3e0ec3199daf', '30ecf9f8-cb08-45e4-af77-317d24171964'),
        ('1c02d056-06c6-47c4-89d6-0122032dfb33', '30ecf9f8-cb08-45e4-af77-317d24171964'),
        ('1c02d056-06c6-47c4-89d6-0122032dfb33', 'c076de71-b095-4e98-a58d-3e0ec3199daf'),
        ('a109ae40-e1d9-42c2-bdd5-a7585ad836b0', 'c076de71-b095-4e98-a58d-3e0ec3199daf');


insert into PUBLIC.TRANSACTION (TAX_AMOUNT, TRANSFER_AMOUNT, OPERATION_DATE, CREDITOR, DEBTOR, ID, DESCRIPTION)
values  (1.25, 25.00, '2023-08-28 19:48:41.242161', 'c076de71-b095-4e98-a58d-3e0ec3199daf', '1c02d056-06c6-47c4-89d6-0122032dfb33', '7bf959f2-5869-40fc-a84d-76804352705d', 'Que la force soit avec toi.'),
        (0.60, 12.00, '2023-08-28 19:48:52.721272', 'a109ae40-e1d9-42c2-bdd5-a7585ad836b0', '1c02d056-06c6-47c4-89d6-0122032dfb33', '8b4d7aab-af7f-4d00-9ad0-8c38033322db', 'Vive le rhum'),
        (1.15, 23.00, '2023-08-28 19:49:12.223782', '30ecf9f8-cb08-45e4-af77-317d24171964', '1c02d056-06c6-47c4-89d6-0122032dfb33', '6d359623-c9c7-4987-bc28-2329d424aba1', 'Tu diras bonjour à Bart.'),
        (null, 50.00, '2023-08-28 19:49:16.160210', '1c02d056-06c6-47c4-89d6-0122032dfb33', '1c02d056-06c6-47c4-89d6-0122032dfb33', '6fb68e1f-6a23-47a5-a0f2-76bf5eb37c9c', 'Fill your PMB account from extern account.'),
        (null, 32.00, '2023-08-28 19:49:20.219684', '1c02d056-06c6-47c4-89d6-0122032dfb33', '1c02d056-06c6-47c4-89d6-0122032dfb33', '989a23c4-d103-49db-8935-c07465739180', 'Transfer money to extern account.'),
        (0.60, 12.00, '2023-08-28 19:49:34.602183', 'a109ae40-e1d9-42c2-bdd5-a7585ad836b0', '1c02d056-06c6-47c4-89d6-0122032dfb33', '394e7387-7ab8-4599-acbe-548dd8a5cf35', 'C''est vraiment bon.'),
        (null, 22.40, '2023-08-28 19:49:43.434307', '1c02d056-06c6-47c4-89d6-0122032dfb33', '1c02d056-06c6-47c4-89d6-0122032dfb33', '9ed2b4ee-f632-4e3a-bafd-0f3976e59cd3', 'Transfer money to extern account.'),
        (0.50, 10.00, '2023-08-28 19:49:59.703364', 'c076de71-b095-4e98-a58d-3e0ec3199daf', '1c02d056-06c6-47c4-89d6-0122032dfb33', '2205d0b3-18ae-4d42-976d-47a25d01e47f', 'Où est Yoda?'),
        (0.06, 1.23, '2023-08-28 19:51:11.094885', 'c076de71-b095-4e98-a58d-3e0ec3199daf', '30ecf9f8-cb08-45e4-af77-317d24171964', 'e35decf0-87a0-4599-9eab-e0ef35fed883', 'T''oh!'),
        (3.00, 59.99, '2023-08-28 19:51:28.098419', '1c02d056-06c6-47c4-89d6-0122032dfb33', '30ecf9f8-cb08-45e4-af77-317d24171964', '746ad919-2102-483a-8466-0d2fc3d670bf', 'T''oh!'),
        (null, 100.00, '2023-08-28 19:51:32.083751', '30ecf9f8-cb08-45e4-af77-317d24171964', '30ecf9f8-cb08-45e4-af77-317d24171964', 'b785a7bf-e658-4dc7-9029-2bd11b3b02d4', 'Fill your PMB account from extern account.'),
        (7.52, 150.50, '2023-08-28 19:51:41.058434', 'c076de71-b095-4e98-a58d-3e0ec3199daf', '30ecf9f8-cb08-45e4-af77-317d24171964', '1b79dcc6-3dae-4474-a5df-456541b3dab7', 'T''oh!'),
        (null, 150.00, '2023-08-28 19:51:46.231111', '30ecf9f8-cb08-45e4-af77-317d24171964', '30ecf9f8-cb08-45e4-af77-317d24171964', '322249e0-56d0-48ca-87b3-be8bc01135e7', 'Fill your PMB account from extern account.'),
        (6.00, 120.00, '2023-08-28 19:51:58.339718', '1c02d056-06c6-47c4-89d6-0122032dfb33', '30ecf9f8-cb08-45e4-af77-317d24171964', '7fec76f9-ca4f-4b4c-a018-ffd68bd88cfa', 'T''oh!'),
        (null, 20.00, '2023-08-28 19:52:01.490290', '30ecf9f8-cb08-45e4-af77-317d24171964', '30ecf9f8-cb08-45e4-af77-317d24171964', '8453cc8e-6113-42ae-89ca-913c526c559c', 'Transfer money to extern account.'),
        (null, 200.00, '2023-08-28 19:53:18.754147', 'c076de71-b095-4e98-a58d-3e0ec3199daf', 'c076de71-b095-4e98-a58d-3e0ec3199daf', 'fe9ca24d-ec50-413b-8b55-6e82bc5f6124', 'Transfer money to extern account.'),
        (3.00, 59.99, '2023-08-28 19:53:25.269539', '1c02d056-06c6-47c4-89d6-0122032dfb33', 'c076de71-b095-4e98-a58d-3e0ec3199daf', '91e252ac-20ea-4973-8e41-8eea8da45660', 'Achat lubrifiant pour sabre.'),
        (null, 25.00, '2023-08-28 19:53:32.137119', 'c076de71-b095-4e98-a58d-3e0ec3199daf', 'c076de71-b095-4e98-a58d-3e0ec3199daf', '1da63fc0-8938-4874-bb19-d8b9c8822a82', 'Fill your PMB account from extern account.'),
        (1.77, 35.49, '2023-08-28 19:54:29.603233', '1c02d056-06c6-47c4-89d6-0122032dfb33', 'c076de71-b095-4e98-a58d-3e0ec3199daf', '5f1a1ff3-b652-48fa-bc28-d062ad8c30ae', 'Achat chiffon pour sabre.'),
        (null, 50.00, '2023-08-28 19:54:34.711178', 'c076de71-b095-4e98-a58d-3e0ec3199daf', 'c076de71-b095-4e98-a58d-3e0ec3199daf', 'a2eafd19-4a72-4475-b0ac-dae2dc7c585e', 'Fill your PMB account from extern account.'),
        (0.50, 9.99, '2023-08-28 19:54:55.294004', 'a109ae40-e1d9-42c2-bdd5-a7585ad836b0', 'c076de71-b095-4e98-a58d-3e0ec3199daf', '5069707b-abbd-439b-9827-26ef39452325', 'Petite gourmandise.');
