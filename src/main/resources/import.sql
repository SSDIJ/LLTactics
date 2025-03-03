-- insert admin (username a, password aa)
INSERT INTO IWUser (id, enabled, roles, username, password)
VALUES (1, TRUE, 'ADMIN,USER', 'a',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
INSERT INTO IWUser (id, enabled, roles, username, password)
VALUES (2, TRUE, 'USER', 'b',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
 
INSERT INTO "PUBLIC"."HEROE" (vida, armadura, faccion, daño, velocidad, descripcion, imagen, nombre) VALUES
(50, 75, 0, 100, 25, U&'Es la primera l\00ednea de defensa.', '/img/units/humans/1. Tanque/heavyinfantry.png', 'Tanque'),
(50, 75, 0, 100, 25, U&'Es la primera l\00ednea de defensa.', '/img/units/humans/1. Tanque/heavyinfantry.png', 'Tanque'),
(80, 70, 0, 40, 200, 'Un estratega nato en el campo de batalla.', '/img/units/humans/2. General/general.png', 'General de Guerra'),
(60, 85, 0, 50, 180, U&'\00c1gil y feroz en combate.', '/img/units/humans/3. Caballero/knight.png', 'Caballero'),
(30, 95, 0, 70, 120, U&'Con una punter\00eda letal.', '/img/units/humans/4. Arquero/longbowman-bow-attack-1.png', 'Arquero'),
(20, 110, 0, 60, 140, 'Un maestro de la magia arcana.', '/img/units/humans/5. Mago/white-mage.png', 'Mago'),
(120, 40, 1, 20, 350, U&'El Drag\00f3n Tanque es una fortaleza viviente con una armadura impenetrable, dise\00f1ado para absorber grandes cantidades de da\00f1o.', '/img/units/dragons/1. DTanque/enforcer-blade.png', U&'Drag\00f3n Tanque'),
(50, 90, 1, 45, 250, 'El Berserker es una furia desatada en el campo de batalla, con ataques devastadores a costa de una defensa reducida.', '/img/units/dragons/2. DBerserker/clasher-blade.png', 'Berserker'),
(90, 75, 1, 35, 280, U&'Con su alabarda m\00e1gica, el Guardi\00e1n equilibra ataque y defensa, protegiendo a sus aliados con su imponente presencia.', '/img/units/dragons/3. DAlabarda/warden.png', U&'Guardi\00e1n'),
(40, 110, 1, 50, 200,  U&'El Incinerador es un drag\00f3n de fuego que abrasa todo a su paso con llamas intensas y ataques explosivos.', '/img/units/dragons/4. DGris/burner.png', 'Incinerador'),
(30, 95, 1, 65, 180,  U&'Especializado en la magia y las runas, el Drag\00f3n Morado es un apoyo para su equipo y una amenaza para el contrario.', '/img/units/dragons/5. DMorado/blademaster.png', U&'Drag\00f3n Morado'),
(60, 50, 2, 30, 300,  'El Troll Gigante es una bestia imponente con una fuerza descomunal.', '/img/units/trolls/1. TTanque/great-troll.png', 'Troll Gigante'),
(30, 100, 2, 70, 150, U&'El Troll Asesino es r\00e1pido y letal, especializado en ataques furtivos.', '/img/units/trolls/2. TAssasin/whelp.png', 'Troll Asesino'),
(50, 80, 2, 40, 200, 'El Troll Guerrero es un combatiente feroz con habilidades equilibradas.', '/img/units/trolls/3. TOfftanque/warrior.png', 'Troll Guerrero'),
(40, 90, 2, 60, 180, U&'El Troll Certero es un experto en ataques a distancia con gran precisi\00f3n.', '/img/units/trolls/4. TRango/lobber.png', 'Troll Certero'),
(20, 70, 2, 80, 160, U&'El Troll Cham\00e1n utiliza poderes m\00edsticos para apoyar a sus aliados.', '/img/units/trolls/5. TMago/shaman.png', U&'Troll Cham\00e1n'),
(70, 60, 3, 30, 250, U&'El Esqueleto Corpulento es un guerrero resistente que puede soportar mucho da\00f1o.', '/img/units/skeletons/1. STanque/draug.png', 'Esqueleto Corpulento'),
(60, 80, 3, 40, 220, 'El Esqueleto General lidera a sus tropas con una presencia intimidante.', '/img/units/skeletons/2. SGeneral/deathknight.png', 'Esqueleto General'),
(50, 90, 3, 50, 200, U&'El Esqueleto a caballo es r\00e1pido y mortal en combate.', '/img/units/skeletons/3. SCaballero/boneknight-attack.png', 'Esqueleto a caballo'),
(40, 100, 3, 60, 180, 'El Esqueleto Arquero es letal a larga distancia con su arco.', '/img/units/skeletons/4. SArquero/banebow-bow.png', 'Esqueleto Arquero'),
(30, 110, 3, 70, 160, 'El Esqueleto mago utiliza poderosos hechizos para devastar a sus enemigos.', '/img/units/skeletons/5. SMago/ancient-lich.png', 'Esqueleto mago'),
(120, 200, 4, 40, 400, 'Una criatura de leyenda con habilidades extraordinarias.', '/img/units/legendary/1. Legendaria/legendary-creature.png', 'Criatura Legendaria');     

-- start id numbering from a value that is larger than any assigned above
ALTER SEQUENCE "PUBLIC"."GEN" RESTART WITH 1024;
