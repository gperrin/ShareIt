-- Initialisation des catégories, laisser même en prod
insert into product_category values (1, 'Animaux');
insert into product_category values (2, 'Bricolage');
insert into product_category values (3, 'Cuisine');
insert into product_category values (4, 'Décoration');
insert into product_category values (5, 'Instruments');
insert into product_category values (6, 'Jardinage');
insert into product_category values (7, 'Livres');
insert into product_category values (8, 'Multimédia');
insert into product_category values (9, 'Véhicules');
insert into product_category values (10, 'Vêtements');




-- Données de test, ne pas les mettre ne prod
insert into sharer (id, firstname, lastname, post_code, age, rating, sex) values (1, 'jean', 'paul', 69100, 25, 0, 'M');
insert into sharer (id, firstname, lastname, post_code, age, rating, sex) values (2, 'mickey', 'mouse', 89000, 89, 0, 'M');

insert into product values (1, 'C est une super perceuse :)', 'Perceuse', 0, 1, 1);
insert into product values (2, 'C est un marteau :)', 'Marteau', 0, 2, 1);
insert into product values (3, 'gant tout neuf', 'Gant de toilette', 0, 1, 2);
insert into product values (4, 'chaussette gauche', 'chaussette', 0, 2, 2);
