-- H2 2.3.232;
;             
CREATE USER IF NOT EXISTS "SA" SALT 'ccb7f449d80cb11a' HASH 'c7f76a289b15a854a87e44f48f49253dd3f4a1011e0ace69961201ce0d24358c' ADMIN;         
CREATE SEQUENCE "PUBLIC"."GEN" START WITH 1 INCREMENT BY 50;  
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_MESSAGE"(
    "RN_" INTEGER NOT NULL,
    "DATE_READ" TIMESTAMP(6),
    "DATE_SENT" TIMESTAMP(6),
    "ID" BIGINT,
    "RECIPIENT_ID" BIGINT,
    "SENDER_ID" BIGINT,
    "TEXT" CHARACTER VARYING(255)
);
ALTER TABLE "PUBLIC"."HTE_MESSAGE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_E" PRIMARY KEY("RN_"); 
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_MESSAGE;             
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HT_HEROE"(
    "ID_HEROE" BIGINT NOT NULL
);        
ALTER TABLE "PUBLIC"."HT_HEROE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3" PRIMARY KEY("ID_HEROE");               
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HT_HEROE;
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_HEROE"(
    "ARMADURA" INTEGER,
    "ARMADURA_ACT" INTEGER,
    U&"DA\00d1O" INTEGER,
    U&"DA\00d1O_ACT" INTEGER,
    "FACCION" INTEGER,
    "PRECIO" INTEGER,
    "VELOCIDAD" INTEGER,
    "VELOCIDAD_ACT" INTEGER,
    "VIDA" INTEGER,
    "VIDA_ACT" INTEGER,
    "HTE_IDENTITY" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ID_HEROE" BIGINT,
    "PROBABILIDAD" FLOAT(53),
    "TIPO" CHARACTER VARYING(31) NOT NULL,
    "DESCRIPCION" CHARACTER VARYING(255),
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255)
);     
ALTER TABLE "PUBLIC"."HTE_HEROE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B" PRIMARY KEY("HTE_IDENTITY");          
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_HEROE;               
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_UNIDAD"(
    "ARMADURA" INTEGER,
    "ARMADURA_ACT" INTEGER,
    U&"DA\00d1O" INTEGER,
    U&"DA\00d1O_ACT" INTEGER,
    "FACCION" INTEGER,
    "PRECIO" INTEGER,
    "VELOCIDAD" INTEGER,
    "VELOCIDAD_ACT" INTEGER,
    "VIDA" INTEGER,
    "VIDA_ACT" INTEGER,
    "HTE_IDENTITY" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ID_HEROE" BIGINT,
    "PROBABILIDAD" FLOAT(53),
    "TIPO" CHARACTER VARYING(31) NOT NULL,
    "DESCRIPCION" CHARACTER VARYING(255),
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255)
);    
ALTER TABLE "PUBLIC"."HTE_UNIDAD" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_9" PRIMARY KEY("HTE_IDENTITY");         
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_UNIDAD;              
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_IWUSER"(
    "ENABLED" BOOLEAN,
    "RN_" INTEGER NOT NULL,
    "ID" BIGINT,
    "FIRST_NAME" CHARACTER VARYING(255),
    "LAST_NAME" CHARACTER VARYING(255),
    "PASSWORD" CHARACTER VARYING(255),
    "ROLES" CHARACTER VARYING(255),
    "USERNAME" CHARACTER VARYING(255)
);        
ALTER TABLE "PUBLIC"."HTE_IWUSER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_7" PRIMARY KEY("RN_");  
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_IWUSER;              
CREATE MEMORY TABLE "PUBLIC"."HEROE"(
    "ARMADURA" INTEGER NOT NULL,
    U&"DA\00d1O" INTEGER NOT NULL,
    "FACCION" INTEGER NOT NULL,
    "PRECIO" INTEGER NOT NULL,
    "PROBABILIDAD" FLOAT(53) NOT NULL,
    "VELOCIDAD" INTEGER NOT NULL,
    "VIDA" INTEGER NOT NULL,
    "ID_HEROE" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 22) NOT NULL,
    "TIPO" CHARACTER VARYING(31) NOT NULL,
    "DESCRIPCION" CHARACTER VARYING(255),
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255)
);   
ALTER TABLE "PUBLIC"."HEROE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID_HEROE");  
-- 21 +/- SELECT COUNT(*) FROM PUBLIC.HEROE;  
INSERT INTO "PUBLIC"."HEROE" VALUES
(75, 150, 0, 3, 0.4, 75, 300, 1, 'Heroe', U&'Firme como una muralla de acero, el Tanque es la primera l\00ednea de defensa, imparable ante los embates enemigos. Con 300 de vida y una resistencia formidable, se erige como el escudo de sus compa\00f1eros en el fragor de la batalla.', '/img/units/humans/1. Tanque/heavyinfantry.png', 'Tanque'),
(45, 112, 0, 4, 0.2, 56, 262, 2, 'Heroe', U&'Sabio en las artes de la estrategia, el General no solo combate, sino que gu\00eda a sus tropas con astucia. Con una armadura ligera pero letal en combate, sus t\00e1cticas dan la ventaja en el campo de batalla.', '/img/units/humans/2. General/general.png', 'General de Guerra'),
(68, 150, 0, 3, 0.4, 45, 225, 3, 'Heroe', U&'\00c1gil y feroz, el Caballero es un guerrero que destaca por su destreza y valor. Con gran habilidad en el combate cuerpo a cuerpo, su espada brilla como un rayo de esperanza para su gente.', '/img/units/humans/3. Caballero/knight.png', 'Caballero'),
(37, 90, 0, 2, 0.4, 68, 150, 4, 'Heroe', U&'El ojo certero del ej\00e9rcito, el Arquero lanza flechas como estrellas fugaces. Con punter\00eda mortal, su habilidad para atacar desde lejos es esencial para desgastar al enemigo antes de que se acerque.', '/img/units/humans/4. Arquero/longbowman-bow-attack-1.png', 'Arquero'),
(30, 135, 0, 5, 0.35, 68, 187, 5, 'Heroe', U&'El conjurador de fuerzas arcanas, el Mago domina la magia como un tejedor de destinos. Su poder radica en su habilidad para controlar el fuego, el hielo y otras artes m\00edsticas, arrasando a sus enemigos desde la distancia.', '/img/units/humans/5. Mago/white-mage.png', 'Mago'),
(80, 145, 1, 6, 0.4, 40, 285, 6, 'Heroe', U&'Un muro viviente, el Drag\00f3n Tanque es una fortaleza de escamas y fuego. Con una armadura impenetrable y un coraz\00f3n de acero, se erige como el centinela m\00e1s temible del campo, capaz de resistir las embestidas m\00e1s feroces.', '/img/units/dragons/1. DTanque/enforcer-blade.png', U&'Drag\00f3n Tanque'),
(20, 140, 1, 5, 0.4, 90, 190, 7, 'Heroe', U&'La furia hecha carne, el Berserker es un drag\00f3n que se lanza al combate sin medida, arrasando con cada golpe. Aunque su defensa es fr\00e1gil, sus ataques devastadores pueden destruir cualquier enemigo que se cruce en su camino.', '/img/units/dragons/2. DBerserker/clasher-blade.png', 'Berserker'),
(75, 135, 1, 5, 0.35, 45, 220, 8, 'Heroe', U&'El protector de los suyos, el Guardi\00e1n blande su alabarda m\00e1gica con maestr\00eda, equilibrando su agresi\00f3n con una defensa formidable. Su presencia en la batalla es un escudo que protege a sus aliados de la muerte misma.', '/img/units/dragons/3. DAlabarda/warden.png', U&'Guardi\00e1n'),
(15, 170, 1, 4, 0.35, 50, 230, 9, 'Heroe', U&'El fuego que arrasa todo a su paso, el Incinerador es un drag\00f3n de llamas intensas que reduce a cenizas a sus enemigos. Sus ataques explosivos convierten el campo de batalla en un infierno, quemando todo lo que toca.', '/img/units/dragons/4. DGris/burner.png', 'Incinerador'),
(15, 170, 1, 4, 0.2, 50, 315, 10, 'Heroe', U&'El hechicero de las runas, el Drag\00f3n Morado combina magia arcana y poder destructivo en una \00fanica entidad. Su habilidad para manipular las runas lo convierte en un aliado invaluable y una amenaza peligrosa para cualquier adversario.', '/img/units/dragons/5. DMorado/blademaster.png', U&'Drag\00f3n Morado'),
(85, 120, 2, 3, 0.35, 70, 280, 11, 'Heroe', U&'Una bestia imparable, el Troll Gigante es una masa de m\00fasculo y furia. Su fuerza descomunal lo convierte en un enemigo temido en las l\00edneas de batalla, capaz de aplastar a cualquier rival con un solo golpe.', '/img/units/trolls/1. TTanque/great-troll.png', 'Troll Gigante'),
(50, 135, 2, 2, 0.35, 60, 250, 12, 'Heroe', U&'La sombra que corta, el Troll Asesino se desliza entre las sombras con agilidad mortal. Sus ataques furtivos y r\00e1pidos dejan a sus enemigos sin aliento, antes de que puedan siquiera reaccionar a su presencia.', '/img/units/trolls/2. TAssasin/whelp.png', 'Troll Asesino'),
(60, 175, 2, 3, 0.4, 50, 275, 13, 'Heroe', U&'El combatiente equilibrado, el Troll Guerrero es un maestro del combate con habilidades tanto ofensivas como defensivas. Su resistencia y destreza le permiten mantenerse firme, enfrentando a sus adversarios con una feroz determinaci\00f3n.', '/img/units/trolls/3. TOfftanque/warrior.png', 'Troll Guerrero');              
INSERT INTO "PUBLIC"."HEROE" VALUES
(30, 110, 2, 2, 0.4, 80, 170, 14, 'Heroe', U&'La mano que nunca falla, el Troll Certero es un experto en el arte de los ataques a distancia. Con gran precisi\00f3n, lanza sus proyectiles como flechas al coraz\00f3n de sus enemigos, eliminando amenazas desde lejos con punter\00eda infalible.', '/img/units/trolls/4. TRango/lobber.png', 'Troll Certero'),
(25, 160, 2, 4, 0.2, 50, 210, 15, 'Heroe', U&'El invocador de fuerzas oscuras, el Troll Cham\00e1n usa su conexi\00f3n m\00edstica con la naturaleza y los esp\00edritus para invocar poderosos hechizos que apoyan a sus aliados y desestabilizan a sus enemigos. Su magia es tan impredecible como las tormentas.', '/img/units/trolls/5. TMago/shaman.png', U&'Troll Cham\00e1n'),
(70, 140, 3, 3, 0.4, 60, 295, 16, 'Heroe', U&'El guerrero eterno, el Esqueleto Corpulento resiste las heridas del tiempo y el combate. Su enorme resistencia lo convierte en una fortaleza ambulante, capaz de soportar grandes da\00f1os y seguir luchando con furia implacable.', '/img/units/skeletons/1. STanque/draug.png', 'Esqueleto Corpulento'),
(55, 125, 3, 4, 0.35, 70, 235, 17, 'Heroe', U&'El l\00edder de los muertos, el Esqueleto General emana una presencia temible que inspira terror y respeto. Su destreza t\00e1ctica y su voluntad inquebrantable lo convierten en el comandante perfecto para las huestes de la no muerte.', '/img/units/skeletons/2. SGeneral/deathknight.png', 'Esqueleto General'),
(75, 165, 3, 4, 0.2, 45, 260, 18, 'Heroe', U&'La sombra r\00e1pida, el Esqueleto a caballo es un jinete espectral que asola el campo con su destreza y velocidad. Mortal en combate, se mueve con la gracia de una tormenta, causando estragos entre las filas enemigas.', '/img/units/skeletons/3. SCaballero/boneknight-attack.png', 'Esqueleto a caballo'),
(25, 130, 3, 3, 0.4, 85, 180, 19, 'Heroe', U&'La flecha de la muerte, el Esqueleto Arquero lanza sus proyectiles con una precisi\00f3n mortal. Desde la distancia, derrumba a sus enemigos uno por uno, como un cazador que acecha a su presa.', '/img/units/skeletons/4. SArquero/banebow-bow.png', 'Esqueleto Arquero'),
(20, 150, 3, 5, 0.35, 55, 220, 20, 'Heroe', 'El hechicero del abismo, el Esqueleto Mago invoca oscuros hechizos que devoran todo a su paso. Su magia arcana es tan poderosa como su cuerpo esqueleto, y arrastra a sus enemigos hacia la oscuridad.', '/img/units/skeletons/5. SMago/ancient-lich.png', 'Esqueleto mago'),
(120, 200, 4, 6, 0.05, 40, 400, 21, 'Heroe', U&'El espectro de fuego eterno, la Criatura Legendaria es una aparici\00f3n envuelta en llamas ardientes, cuya existencia desaf\00eda la realidad. Con habilidades extraordinarias, su poder es tan vasto como las llamas que consume, arrasando con todo a su paso.', '/img/units/legendary/fireghost/fireghost.png', 'Criatura Legendaria');          
CREATE MEMORY TABLE "PUBLIC"."IWUSER"(
    "ENABLED" BOOLEAN NOT NULL,
    "ID" BIGINT NOT NULL,
    "FIRST_NAME" CHARACTER VARYING(255),
    "LAST_NAME" CHARACTER VARYING(255),
    "PASSWORD" CHARACTER VARYING(255) NOT NULL,
    "ROLES" CHARACTER VARYING(255),
    "USERNAME" CHARACTER VARYING(255) NOT NULL
);               
ALTER TABLE "PUBLIC"."IWUSER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_81" PRIMARY KEY("ID");      
-- 2 +/- SELECT COUNT(*) FROM PUBLIC.IWUSER;  
INSERT INTO "PUBLIC"."IWUSER" VALUES
(TRUE, 1, NULL, NULL, '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W', 'ADMIN,USER', 'a'),
(TRUE, 2, NULL, NULL, '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W', 'USER', 'b');          
CREATE MEMORY TABLE "PUBLIC"."JUGADOR"(
    "BANEADO" BOOLEAN NOT NULL,
    "FACCION_FAVORITA" INTEGER NOT NULL,
    "INDICE_RANKING" INTEGER NOT NULL,
    "PARTIDAS_GANADAS" INTEGER NOT NULL,
    "PARTIDAS_PERDIDAS" INTEGER NOT NULL,
    "PUNTUACION" INTEGER NOT NULL,
    "FECHA_BANEO" TIMESTAMP(6),
    "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "PARTIDA_ID_PARTIDA" BIGINT,
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255),
    "RAZON_BANEO" CHARACTER VARYING(255)
);    
ALTER TABLE "PUBLIC"."JUGADOR" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_D" PRIMARY KEY("ID");      
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.JUGADOR; 
CREATE MEMORY TABLE "PUBLIC"."JUGADOR_MAS_JUGADOS"(
    "JUGADOR_ID" BIGINT NOT NULL,
    "MAS_JUGADOS_ID_HEROE" BIGINT NOT NULL
);        
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.JUGADOR_MAS_JUGADOS;     
CREATE MEMORY TABLE "PUBLIC"."MENSAJE"(
    "FECHA_HORA" TIMESTAMP(6),
    "ID_JUGADOR" BIGINT,
    "ID_MENSAJE" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ID_PARTIDA" BIGINT,
    "CONTENIDO" CHARACTER VARYING(255)
);     
ALTER TABLE "PUBLIC"."MENSAJE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_6" PRIMARY KEY("ID_MENSAJE");              
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.MENSAJE; 
CREATE MEMORY TABLE "PUBLIC"."MESSAGE"(
    "DATE_READ" TIMESTAMP(6),
    "DATE_SENT" TIMESTAMP(6),
    "ID" BIGINT NOT NULL,
    "RECIPIENT_ID" BIGINT,
    "SENDER_ID" BIGINT,
    "TEXT" CHARACTER VARYING(255)
);  
ALTER TABLE "PUBLIC"."MESSAGE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_63" PRIMARY KEY("ID");     
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.MESSAGE; 
CREATE MEMORY TABLE "PUBLIC"."OBJETO"(
    "ARMADURA" INTEGER NOT NULL,
    U&"DA\00d1O" INTEGER NOT NULL,
    "PRECIO" INTEGER NOT NULL,
    "VELOCIDAD" INTEGER NOT NULL,
    "VIDA" INTEGER NOT NULL,
    "ID_OBJETO" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ID_UNIDAD" BIGINT,
    "DESCRIPCION" CHARACTER VARYING(255),
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255)
);             
ALTER TABLE "PUBLIC"."OBJETO" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8A" PRIMARY KEY("ID_OBJETO");               
-- 20 +/- SELECT COUNT(*) FROM PUBLIC.OBJETO; 
INSERT INTO "PUBLIC"."OBJETO" VALUES
(2, 3, 1, 4, 5, 1, NULL, U&'Un collar m\00edstico con el s\00edmbolo del ankh.', '/img/items/ankh-necklace.png', 'Collar Ankh'),
(0, 1, 2, 3, 2, 2, NULL, 'Un libro con conocimientos antiguos.', '/img/items/book5.png', 'Libro 5'),
(1, 5, 3, 2, 4, 3, NULL, 'Una espada envuelta en llamas.', '/img/items/flame-sword.png', 'Espada Llameante'),
(1, 4, 3, 3, 3, 4, NULL, 'Una lanza decorada con detalles intrincados.', '/img/items/spear-fancy.png', 'Lanza Elegante'),
(2, 5, 4, 2, 4, 5, NULL, 'Una espada bendecida con poder divino.', '/img/items/sword-holy.png', 'Espada Sagrada'),
(5, 0, 4, 1, 5, 6, NULL, U&'Armadura hecha de oro puro, proporciona una gran protecci\00f3n.', '/img/items/armor-golden.png', 'Armadura Dorada'),
(0, 3, 2, 5, 2, 7, NULL, U&'Un arco poderoso con gran precisi\00f3n.', '/img/items/bow-2.png', 'Arco 2'),
(0, 0, 1, 4, 1, 8, NULL, U&'Una flor rara con propiedades m\00e1gicas.', '/img/items/flower3.png', U&'Flor M\00e1gica'),
(1, 3, 2, 3, 2, 9, NULL, 'Una lanza ligera para lanzar.', '/img/items/spear-javelin.png', 'Lanza Jabalina'),
(1, 4, 3, 4, 2, 10, NULL, U&'Una espada peque\00f1a y r\00e1pida para golpes r\00e1pidos.', '/img/items/sword-short.png', 'Espada Corta'),
(3, 0, 2, 1, 3, 11, NULL, U&'Armadura b\00e1sica para protecci\00f3n.', '/img/items/armor.png', 'Armadura'),
(0, 4, 3, 5, 3, 12, NULL, 'Un arco hecho de cristal encantado.', '/img/items/bow-crystal-2.png', 'Arco Cristal 2'),
(2, 5, 3, 1, 4, 13, NULL, 'Un martillo grabado con runas antiguas.', '/img/items/hammer-runic.png', U&'Martillo R\00fanico'),
(1, 3, 2, 3, 2, 14, NULL, U&'Una lanza est\00e1ndar para combate.', '/img/items/spear.png', 'Lanza'),
(2, 5, 4, 2, 4, 15, NULL, U&'Una espada que roba la energ\00eda vital de los enemigos.', '/img/items/sword-wraith.png', 'Espada Espectral'),
(1, 4, 3, 2, 3, 16, NULL, 'Un hacha grande para combate.', '/img/items/axe-2.png', 'Hacha de Batalla'),
(0, 4, 3, 4, 2, 17, NULL, U&'Un arco hecho de cristal m\00e1gico.', '/img/items/bow-crystal.png', 'Arco de Cristal'),
(0, 1, 2, 3, 2, 18, NULL, U&'Un collar hecho de huesos de criaturas m\00edticas.', '/img/items/necklace-bone.png', 'Collar de Hueso'),
(0, 5, 3, 2, 5, 19, NULL, U&'Un bast\00f3n m\00e1gico con poderosos hechizos.', '/img/items/staff-2.png', U&'Bast\00f3n de Poder'),
(1, 3, 2, 3, 2, 20, NULL, U&'Una espada b\00e1sica para combate cuerpo a cuerpo.', '/img/items/sword.png', 'Espada');       
CREATE MEMORY TABLE "PUBLIC"."PARTIDA"(
    "DUARCION_MIN" INTEGER NOT NULL,
    "ID_PARTIDA" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "NOMBRE" CHARACTER VARYING(255)
);      
ALTER TABLE "PUBLIC"."PARTIDA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F" PRIMARY KEY("ID_PARTIDA");              
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.PARTIDA; 
CREATE MEMORY TABLE "PUBLIC"."UNIDAD"(
    "ARMADURA_ACT" INTEGER NOT NULL,
    U&"DA\00d1O_ACT" INTEGER NOT NULL,
    "VELOCIDAD_ACT" INTEGER NOT NULL,
    "VIDA_ACT" INTEGER NOT NULL,
    "ID_HEROE" BIGINT NOT NULL
);             
ALTER TABLE "PUBLIC"."UNIDAD" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_95" PRIMARY KEY("ID_HEROE");
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.UNIDAD;  
ALTER TABLE "PUBLIC"."IWUSER" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8" UNIQUE NULLS DISTINCT ("USERNAME");      
ALTER TABLE "PUBLIC"."JUGADOR_MAS_JUGADOS" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_7D" UNIQUE NULLS DISTINCT ("MAS_JUGADOS_ID_HEROE");            
ALTER TABLE "PUBLIC"."UNIDAD" ADD CONSTRAINT "PUBLIC"."FKDY1I2F6SA1WT1XEFHB8R0AY6D" FOREIGN KEY("ID_HEROE") REFERENCES "PUBLIC"."HEROE"("ID_HEROE") NOCHECK;  
ALTER TABLE "PUBLIC"."JUGADOR_MAS_JUGADOS" ADD CONSTRAINT "PUBLIC"."FK1A1AVARNTPETN67JILWTYHRV1" FOREIGN KEY("MAS_JUGADOS_ID_HEROE") REFERENCES "PUBLIC"."HEROE"("ID_HEROE") NOCHECK;         
ALTER TABLE "PUBLIC"."MENSAJE" ADD CONSTRAINT "PUBLIC"."FK8LDYBLD1F83MEGNINQW66AKRA" FOREIGN KEY("ID_JUGADOR") REFERENCES "PUBLIC"."JUGADOR"("ID") NOCHECK;   
ALTER TABLE "PUBLIC"."JUGADOR" ADD CONSTRAINT "PUBLIC"."FKANBD4NET7GXNQSPDA6C1L9WJO" FOREIGN KEY("PARTIDA_ID_PARTIDA") REFERENCES "PUBLIC"."PARTIDA"("ID_PARTIDA") NOCHECK;   
ALTER TABLE "PUBLIC"."MESSAGE" ADD CONSTRAINT "PUBLIC"."FKNCAXW3LCT4F38N1NXO5BCAR54" FOREIGN KEY("RECIPIENT_ID") REFERENCES "PUBLIC"."IWUSER"("ID") NOCHECK;  
ALTER TABLE "PUBLIC"."MESSAGE" ADD CONSTRAINT "PUBLIC"."FKXQR9WQ16K3403YBEPWGPGY0N" FOREIGN KEY("SENDER_ID") REFERENCES "PUBLIC"."IWUSER"("ID") NOCHECK;      
ALTER TABLE "PUBLIC"."JUGADOR_MAS_JUGADOS" ADD CONSTRAINT "PUBLIC"."FKLEH2JJ2B30H5IQI2V3OAMJY6X" FOREIGN KEY("JUGADOR_ID") REFERENCES "PUBLIC"."JUGADOR"("ID") NOCHECK;       
ALTER TABLE "PUBLIC"."OBJETO" ADD CONSTRAINT "PUBLIC"."FKR26CHL9PE7ML5OD5M588GV5DT" FOREIGN KEY("ID_UNIDAD") REFERENCES "PUBLIC"."UNIDAD"("ID_HEROE") NOCHECK;
ALTER TABLE "PUBLIC"."MENSAJE" ADD CONSTRAINT "PUBLIC"."FK1SFG2XG3G85PJP17EE6JK85QO" FOREIGN KEY("ID_PARTIDA") REFERENCES "PUBLIC"."PARTIDA"("ID_PARTIDA") NOCHECK;           
