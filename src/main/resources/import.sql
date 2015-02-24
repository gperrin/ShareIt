-- Initialisation des catégories, laisser même en prod
insert into product_category values (1, 'outils');
insert into product_category values (2, 'ustensiles de cuisine');
insert into product_category values (3, 'vêtements');
insert into product_category values (4, 'véhicules');


-- Données de test, ne pas les mettre ne prod
insert into sharer (id, firstname, lastname, post_code, age, rating, sex) values (1, 'jean', 'paul', 69100, 25, 0, 'M');
insert into sharer (id, firstname, lastname, post_code, age, rating, sex) values (2, 'mickey', 'mouse', 69100, 89, 0, 'M');

insert into product values (1, 'C est une super perceuse :)', 'Perceuse', 0, 1, 1);
