
-- insert admin (username a, password aa)
--CREATE MEMORY TABLE "PUBLIC"."JUGADOR"(
--    "ENABLED" BOOLEAN,
--    "ESTADO" INTEGER,
--    "FACCION_FAVORITA" INTEGER,
--    "INDICE_RANKING" INTEGER,
--    "PARTIDAS_GANADAS" INTEGER,
--    "PARTIDAS_PERDIDAS" INTEGER,
--    "PUNTUACION" INTEGER,
--    "FECHA_BANEO" TIMESTAMP(6),
--    "ID" BIGINT,
--    "IDFOTO_PERFIL" BIGINT,
--    "FIRST_NAME" CHARACTER VARYING(255),
--    "LAST_NAME" CHARACTER VARYING(255),
--    "PASSWORD" CHARACTER VARYING(255),
--    "RAZON_BANEO" CHARACTER VARYING(255),
--    "ROLES" CHARACTER VARYING(255),
--    "USERNAME" CHARACTER VARYING(255)
--);    

INSERT INTO "IWUSER"
(ID, IDFOTO_PERFIL, ENABLED, ESTADO, FACCION_FAVORITA, FECHA_BANEO, FIRST_NAME, LAST_NAME, PARTIDAS_GANADAS, PARTIDAS_PERDIDAS, PASSWORD, PUNTUACION, RAZON_BANEO, ROLES, USERNAME)
VALUES
(1, 5, TRUE, 0, 0, null, 'userA', 'aa', 7, 3, '{bcrypt}$2a$10$GU4mO.Uxc.tRP3.DsrBIvO3543uERYAvJwkuplrpFTAYdXoteTFOm', 2134, null, 'ADMIN,USER', 'a'),
(2, 2, TRUE, 0, 0, null, 'userB', 'aa', 3, 10, '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W', 3675, null, 'USER', 'b'),
(3, 1, TRUE, 0, 0, null, 'Jugador', 'Uno', 5, 5, '{bcrypt}$2a$10$example', 1000, null, 'USER', 'jugador1'),
(4, 1, TRUE, 0, 0, null, 'Jugador', 'Dos', 8, 2, '{bcrypt}$2a$10$example', 1200, null, 'USER', 'jugador2'),
(5, 1, TRUE, 0, 0, null, 'Jugador', 'Tres', 3, 7, '{bcrypt}$2a$10$example', 800, null, 'USER', 'jugador3'),
(6, 1, TRUE, 0, 0, null, 'Jugador', 'Cuatro', 9, 1, '{bcrypt}$2a$10$example', 1300, null, 'USER', 'jugador4'),
(7, 1, TRUE, 0, 0, null, 'Jugador', 'Cinco', 2, 8, '{bcrypt}$2a$10$example', 700, null, 'USER', 'jugador5'),
(8, 1, TRUE, 0, 0, null, 'Jugador', 'Seis', 6, 4, '{bcrypt}$2a$10$example', 1100, null, 'USER', 'jugador6'),
(9, 1, TRUE, 0, 0, null, 'Jugador', 'Siete', 4, 6, '{bcrypt}$2a$10$example', 900, null, 'USER', 'jugador7'),
(10, 1, TRUE, 0, 0, null, 'Jugador', 'Ocho', 7, 3, '{bcrypt}$2a$10$example', 1150, null, 'USER', 'jugador8'),
(11, 1, TRUE, 0, 0, null, 'Jugador', 'Nueve', 1, 9, '{bcrypt}$2a$10$example', 650, null, 'USER', 'jugador9'),
(12, 1, TRUE, 0, 0, null, 'Jugador', 'Diez', 10, 0, '{bcrypt}$2a$10$example', 1400, null, 'USER', 'jugador10'),
(13, 5, TRUE, 0, 0, null, 'Admin', 'Administrez', 7, 3, '{bcrypt}$2a$10$GU4mO.Uxc.tRP3.DsrBIvO3543uERYAvJwkuplrpFTAYdXoteTFOm', 1134, null, 'ADMIN,USER', 'pruebaAdmin123'),
(14, 2, TRUE, 0, 0, null, 'Usuario', 'Usuariez', 3, 10, '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W', 675, null, 'USER', 'usuarioMolon77');



INSERT INTO "PUBLIC"."FACCION_USOS" 
(FACCION, VECES_USADO, ID, JID) 
VALUES
(0, 3,1, 1),
(1, 5,2, 1),
(2, 2,3, 1),
(3, 0,4, 1),
(4, 1,5, 1),
(0, 1,6, 2),
(1, 0,7, 2),
(2, 4,8, 2),
(3, 2,9, 2),
(4, 3,10, 2);

INSERT INTO "PUBLIC"."PARTIDA"
(EN_CURSO, GANADOR, PERDEDOR, FIN, ID_PARTIDA, INICIO, JID1, JID2, ESTADO, GAME_ROOM_ID)
VALUES
(FALSE, 1, 2, null, 1, null, 1, 2, '', '');

-- start id numbering from a value that is larger than any assigned above
ALTER SEQUENCE "PUBLIC"."GEN" RESTART WITH 1024;

-- H2 2.3.232;
INSERT INTO "PUBLIC"."HEROE" (vida, armadura, faccion, precio, probabilidad, daño, velocidad, tipo, descripcion, imagen, nombre) VALUES
(300, 75, 0, 3, 0.4, 150, 75, 'Heroe', U&'Firme como una muralla de acero, el Tanque es la primera l\00ednea de defensa, imparable ante los embates enemigos. Con 300 de vida y una resistencia formidable, se erige como el escudo de sus compa\00f1eros en el fragor de la batalla.', '/img/units/humans/1. Tanque/heavyinfantry.png', 'Tanque'),
(262, 45, 0, 4, 0.2, 112, 56, 'Heroe', U&'Sabio en las artes de la estrategia, el General no solo combate, sino que gu\00eda a sus tropas con astucia. Con una armadura ligera pero letal en combate, sus t\00e1cticas dan la ventaja en el campo de batalla.', '/img/units/humans/2. General/general.png', 'General de Guerra'),
(225, 68, 0, 3, 0.4, 150, 45, 'Heroe', U&'\00c1gil y feroz, el Caballero es un guerrero que destaca por su destreza y valor. Con gran habilidad en el combate cuerpo a cuerpo, su espada brilla como un rayo de esperanza para su gente.', '/img/units/humans/3. Caballero/knight.png', 'Caballero'),
(150, 37, 0, 2, 0.4, 90, 68, 'Heroe', U&'El ojo certero del ej\00e9rcito, el Arquero lanza flechas como estrellas fugaces. Con punter\00eda mortal, su habilidad para atacar desde lejos es esencial para desgastar al enemigo antes de que se acerque.', '/img/units/humans/4. Arquero/longbowman-bow-attack-1.png', 'Arquero'),
(187, 30, 0, 5, 0.35, 135, 45, 'Heroe', U&'El conjurador de fuerzas arcanas, el Mago domina la magia como un tejedor de destinos. Su poder radica en su habilidad para controlar el fuego, el hielo y otras artes m\00edsticas, arrasando a sus enemigos desde la distancia.', '/img/units/humans/5. Mago/white-mage.png', 'Mago'),
(285, 80, 1, 6, 0.4, 145, 40, 'Heroe', U&'Un muro viviente, el Drag\00f3n Tanque es una fortaleza de escamas y fuego. Con una armadura impenetrable y un coraz\00f3n de acero, se erige como el centinela m\00e1s temible del campo, capaz de resistir las embestidas m\00e1s feroces.', '/img/units/dragons/1. DTanque/enforcer-blade.png', U&'Drag\00f3n Tanque'),
(190, 20, 1, 5, 0.4, 140, 90, 'Heroe', U&'La furia hecha carne, el Berserker es un drag\00f3n que se lanza al combate sin medida, arrasando con cada golpe. Aunque su defensa es fr\00e1gil, sus ataques devastadores pueden destruir cualquier enemigo que se cruce en su camino.', '/img/units/dragons/2. DBerserker/clasher-blade.png', 'Berserker'),
(220, 75, 1, 5, 0.35, 135, 45, 'Heroe', U&'El protector de los suyos, el Guardi\00e1n blande su alabarda m\00e1gica con maestr\00eda, equilibrando su agresi\00f3n con una defensa formidable. Su presencia en la batalla es un escudo que protege a sus aliados de la muerte misma.', '/img/units/dragons/3. DAlabarda/warden.png', U&'Guardi\00e1n'),
(230, 15, 1, 4, 0.35, 170, 50, 'Heroe', U&'El fuego que arrasa todo a su paso, el Incinerador es un drag\00f3n de llamas intensas que reduce a cenizas a sus enemigos. Sus ataques explosivos convierten el campo de batalla en un infierno, quemando todo lo que toca.', '/img/units/dragons/4. DGris/burner.png', 'Incinerador'),
(315, 15, 1, 4, 0.2, 170, 50, 'Heroe', U&'El hechicero de las runas, el Drag\00f3n Morado combina magia arcana y poder destructivo en una \00fanica entidad. Su habilidad para manipular las runas lo convierte en un aliado invaluable y una amenaza peligrosa para cualquier adversario.', '/img/units/dragons/5. DMorado/blademaster.png', U&'Drag\00f3n Morado'),
(280, 85, 2, 3, 0.35, 120, 70, 'Heroe', U&'Una bestia imparable, el Troll Gigante es una masa de m\00fasculo y furia. Su fuerza descomunal lo convierte en un enemigo temido en las l\00edneas de batalla, capaz de aplastar a cualquier rival con un solo golpe.', '/img/units/trolls/1. TTanque/great-troll.png', 'Troll Gigante'),
(250, 50, 2, 2, 0.35, 135, 60, 'Heroe', U&'La sombra que corta, el Troll Asesino se desliza entre las sombras con agilidad mortal. Sus ataques furtivos y r\00e1pidos dejan a sus enemigos sin aliento, antes de que puedan siquiera reaccionar a su presencia.', '/img/units/trolls/2. TAssasin/whelp.png', 'Troll Asesino'),
(275, 60, 2, 3, 0.4, 175, 50, 'Heroe', U&'El combatiente equilibrado, el Troll Guerrero es un maestro del combate con habilidades tanto ofensivas como defensivas. Su resistencia y destreza le permiten mantenerse firme, enfrentando a sus adversarios con una feroz determinaci\00f3n.', '/img/units/trolls/3. TOfftanque/warrior.png', 'Troll Guerrero'),              
(170, 30, 2, 2, 0.4, 110, 80, 'Heroe', U&'La mano que nunca falla, el Troll Certero es un experto en el arte de los ataques a distancia. Con gran precisi\00f3n, lanza sus proyectiles como flechas al coraz\00f3n de sus enemigos, eliminando amenazas desde lejos con punter\00eda infalible.', '/img/units/trolls/4. TRango/lobber.png', 'Troll Certero'),
(210, 25, 2, 4, 0.2, 160, 50, 'Heroe', U&'El invocador de fuerzas oscuras, el Troll Cham\00e1n usa su conexi\00f3n m\00edstica con la naturaleza y los esp\00edritus para invocar poderosos hechizos que apoyan a sus aliados y desestabilizan a sus enemigos. Su magia es tan impredecible como las tormentas.', '/img/units/trolls/5. TMago/shaman.png', U&'Troll Cham\00e1n'),
(295, 70, 3, 3, 0.4, 140, 60, 'Heroe', U&'El guerrero eterno, el Esqueleto Corpulento resiste las heridas del tiempo y el combate. Su enorme resistencia lo convierte en una fortaleza ambulante, capaz de soportar grandes da\00f1os y seguir luchando con furia implacable.', '/img/units/skeletons/1. STanque/draug.png', 'Esqueleto Corpulento'),
(235, 55, 3, 4, 0.35, 125,70, 'Heroe', U&'El l\00edder de los muertos, el Esqueleto General emana una presencia temible que inspira terror y respeto. Su destreza t\00e1ctica y su voluntad inquebrantable lo convierten en el comandante perfecto para las huestes de la no muerte.', '/img/units/skeletons/2. SGeneral/deathknight.png', 'Esqueleto General'),
(260, 75, 3, 4, 0.2, 165, 45, 'Heroe', U&'La sombra r\00e1pida, el Esqueleto a caballo es un jinete espectral que asola el campo con su destreza y velocidad. Mortal en combate, se mueve con la gracia de una tormenta, causando estragos entre las filas enemigas.', '/img/units/skeletons/3. SCaballero/boneknight-attack.png', 'Esqueleto a caballo'),
(180, 25, 3, 3, 0.4, 130, 85, 'Heroe', U&'La flecha de la muerte, el Esqueleto Arquero lanza sus proyectiles con una precisi\00f3n mortal. Desde la distancia, derrumba a sus enemigos uno por uno, como un cazador que acecha a su presa.', '/img/units/skeletons/4. SArquero/banebow-bow.png', 'Esqueleto Arquero'),
(220, 20, 3, 5, 0.35, 150, 55, 'Heroe', 'El hechicero del abismo, el Esqueleto Mago invoca oscuros hechizos que devoran todo a su paso. Su magia arcana es tan poderosa como su cuerpo esqueleto, y arrastra a sus enemigos hacia la oscuridad.', '/img/units/skeletons/5. SMago/ancient-lich.png', 'Esqueleto mago'),
(400, 120, 4, 6, 0.05, 200, 40, 'Heroe', U&'El espectro de fuego eterno, la Criatura Legendaria es una aparici\00f3n envuelta en llamas ardientes, cuya existencia desaf\00eda la realidad. Con habilidades extraordinarias, su poder es tan vasto como las llamas que consume, arrasando con todo a su paso.', '/img/units/legendary/fireghost/fireghost.png', 'Criatura Legendaria');          

INSERT INTO "PUBLIC"."HEROE_USOS" 
(VECES_USADO, HID, ID, JID) 
VALUES
(0, 3,1, 1),
(1, 5,2, 1),
(2, 2,3, 1),
(3, 6,4, 1),
(4, 1,5, 1),
(0, 1,6, 2),
(1, 10,7, 2),
(2, 4,8, 2),
(3, 2,9, 2),
(4, 3,10, 2);  

INSERT INTO "PUBLIC"."IWUSER_MAS_JUGADOS" 
(MAS_JUGADOS_ID_HEROE, USER_ID)
VALUES
(4, 1),
(10, 2);

-- 1 +/- SELECT COUNT(*) FROM PUBLIC.OBJETO;  
INSERT INTO "PUBLIC"."OBJETO" (ARMADURA, DAÑO, PRECIO, VELOCIDAD, VIDA, ID_UNIDAD, DESCRIPCION, IMAGEN, NOMBRE) VALUES
(20, 35, 4, 30, 45, NULL, 'Un collar místico con el símbolo del ankh.', '/img/items/ankh-necklace.png', 'Collar Ankh'),
(10, 15, 3, 20, 25, NULL, 'Un libro con conocimientos antiguos.', '/img/items/book5.png', 'Libro 5'),
(15, 55, 2, 40, 35, NULL, 'Una espada envuelta en llamas.', '/img/items/flame-sword.png', 'Espada Llameante'),
(18, 45, 3, 35, 30, NULL, 'Una lanza decorada con detalles intrincados.', '/img/items/spear-fancy.png', 'Lanza Elegante'),
(25, 50, 2, 38, 50, NULL, 'Una espada bendecida con poder divino.', '/img/items/sword-holy.png', 'Espada Sagrada'),
(30, 10, 5, 15, 60, NULL, 'Armadura hecha de oro puro, proporciona una gran protección.', '/img/items/armor-golden.png', 'Armadura Dorada'),
(12, 40, 5, 45, 28, NULL, 'Un arco poderoso con gran precisión.', '/img/items/bow-2.png', 'Arco 2'),
(5, 5, 4, 15, 10, NULL, 'Una flor rara con propiedades mágicas.', '/img/items/flower3.png', 'Flor Mágica'),
(15, 40, 3, 50, 25, NULL, 'Una lanza ligera para lanzar.', '/img/items/spear-javelin.png', 'Lanza Jabalina'),
(20, 42, 4, 48, 30, NULL, 'Una espada pequeña y rápida para golpes rápidos.', '/img/items/sword-short.png', 'Espada Corta'),
(30, 5, 1, 10, 40,NULL, 'Armadura básica para protección.', '/img/items/armor.png', 'Armadura'),
(10, 45, 5, 52, 28, NULL, 'Un arco hecho de cristal encantado.', '/img/items/bow-crystal-2.png', 'Arco Cristal 2'),
(30, 48, 1, 32, 50, NULL, 'Un martillo grabado con runas antiguas.', '/img/items/hammer-runic.png', 'Martillo Rúnico'),
(18, 35, 3, 30, 30, NULL, 'Una lanza estándar para combate.', '/img/items/spear.png', 'Lanza'),
(25, 52, 2, 33, 45, NULL, 'Una espada que roba la energía vital de los enemigos.', '/img/items/sword-wraith.png', 'Espada Espectral'),
(22, 50, 2, 38, 25, NULL, 'Un hacha grande para combate.', '/img/items/axe-2.png', 'Hacha de Batalla'),
(12, 47, 4, 50, 30, NULL, 'Un arco hecho de cristal mágico.', '/img/items/bow-crystal.png', 'Arco de Cristal'),
(8, 30, 3, 20, 22, NULL, 'Un collar hecho de huesos de criaturas míticas.', '/img/items/necklace-bone.png', 'Collar de Hueso'),
(15, 38, 2, 25, 35, NULL, 'Un bastón mágico con poderosos hechizos.', '/img/items/staff-2.png', 'Bastón de Poder'),
(15, 35, 3, 30, 28, NULL, 'Una espada básica para combate cuerpo a cuerpo.', '/img/items/sword.png', 'Espada');


INSERT INTO "PUBLIC"."CONFIG_PARTIDA" 
(DANYO_VICTORIA, ESTRELLAS_INI, ESTRELLAS_RONDA, PRECIO_REFRESCAR, VIDA_INI, PUNTOS_POR_PARTIDA)
VALUES
(1, 20, 15, 2, 3, 20);

INSERT INTO MESSAGE (ID, ID_PARTIDA, RECIPIENT_ID, SENDER_ID, TOPIC_ID, TEXT, DATE_SENT, DATE_READ)
VALUES (1025, NULL, 1, 2, NULL, 'hola buenas tardes', NULL, NULL);

INSERT INTO MESSAGE (ID, ID_PARTIDA, RECIPIENT_ID, SENDER_ID, TOPIC_ID, TEXT, DATE_SENT, DATE_READ)
VALUES (1026, NULL, 2, 1, NULL, 'callate un rato anda', NULL, NULL);

INSERT INTO MESSAGE (ID, ID_PARTIDA, RECIPIENT_ID, SENDER_ID, TOPIC_ID, TEXT, DATE_SENT, DATE_READ)
VALUES (1027, NULL, 1, 2, NULL, 'perdon', NULL, NULL);
