-- insert admin (username a, password aa)
INSERT INTO IWUser (id, enabled, roles, username, password)
VALUES (1, TRUE, 'ADMIN,USER', 'a',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
INSERT INTO IWUser (id, enabled, roles, username, password)
VALUES (2, TRUE, 'USER', 'b',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
 

-- H2 2.3.232;
;             
CREATE USER IF NOT EXISTS "SA" SALT '95657a236ec34f5f' HASH 'c1b4850728e757893760e9affdeb681befb37622e8748ac7792d7c625ab840f0' ADMIN;         
CREATE SEQUENCE "PUBLIC"."GEN" START WITH 1 RESTART WITH 1024 INCREMENT BY 50;
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HT_HEROE"(
    "ID_HEROE" BIGINT NOT NULL
);        
ALTER TABLE "PUBLIC"."HT_HEROE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_3" PRIMARY KEY("ID_HEROE");               
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HT_HEROE;
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_HEROE"(
    "ARMADURA" INTEGER,
    U&"DA\00d1O" INTEGER,
    "FACCION" INTEGER,
    "PRECIO" INTEGER,
    "VELOCIDAD" INTEGER,
    "VIDA" INTEGER,
    "HTE_IDENTITY" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ARMADURA_ACT" FLOAT(53),
    U&"DA\00d1O_ACT" FLOAT(53),
    "ID_HEROE" BIGINT,
    "VELOCIDAD_ACT" FLOAT(53),
    "VIDA_ACT" FLOAT(53),
    "TIPO" CHARACTER VARYING(31) NOT NULL,
    "DESCRIPCION" CHARACTER VARYING(255),
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255)
);            
ALTER TABLE "PUBLIC"."HTE_HEROE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_B" PRIMARY KEY("HTE_IDENTITY");          
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_HEROE;               
CREATE GLOBAL TEMPORARY TABLE "PUBLIC"."HTE_UNIDAD"(
    "ARMADURA" INTEGER,
    U&"DA\00d1O" INTEGER,
    "FACCION" INTEGER,
    "PRECIO" INTEGER,
    "VELOCIDAD" INTEGER,
    "VIDA" INTEGER,
    "HTE_IDENTITY" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "ARMADURA_ACT" FLOAT(53),
    U&"DA\00d1O_ACT" FLOAT(53),
    "ID_HEROE" BIGINT,
    "VELOCIDAD_ACT" FLOAT(53),
    "VIDA_ACT" FLOAT(53),
    "TIPO" CHARACTER VARYING(31) NOT NULL,
    "DESCRIPCION" CHARACTER VARYING(255),
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255)
);           
ALTER TABLE "PUBLIC"."HTE_UNIDAD" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_9" PRIMARY KEY("HTE_IDENTITY");         
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.HTE_UNIDAD;              
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
    "VELOCIDAD" INTEGER NOT NULL,
    "VIDA" INTEGER NOT NULL,
    "ID_HEROE" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "TIPO" CHARACTER VARYING(31) NOT NULL,
    "DESCRIPCION" CHARACTER VARYING(255),
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255)
);           
ALTER TABLE "PUBLIC"."HEROE" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_4" PRIMARY KEY("ID_HEROE");  
-- 21 +/- SELECT COUNT(*) FROM PUBLIC.HEROE;  
INSERT INTO "PUBLIC"."HEROE" VALUES
(50, 75, 0, 3, 100, 25, 1, 'Heroe', U&'Es la primera l\00ednea de defensa.', '/img/units/humans/1. Tanque/heavyinfantry.png', 'Tanque'),
(80, 70, 0, 4, 40, 200, 2, 'Heroe', 'Un estratega nato en el campo de batalla.', '/img/units/humans/2. General/general.png', 'General de Guerra'),
(60, 85, 0, 3, 50, 180, 3, 'Heroe', U&'\00c1gil y feroz en combate.', '/img/units/humans/3. Caballero/knight.png', 'Caballero'),
(30, 95, 0, 2, 70, 120, 4, 'Heroe', U&'Con una punter\00eda letal.', '/img/units/humans/4. Arquero/longbowman-bow-attack-1.png', 'Arquero'),
(20, 110, 0, 5, 60, 140, 5, 'Heroe', 'Un maestro de la magia arcana.', '/img/units/humans/5. Mago/white-mage.png', 'Mago'),
(120, 40, 1, 6, 20, 350, 6, 'Heroe', U&'El Drag\00f3n Tanque es una fortaleza viviente con una armadura impenetrable, dise\00f1ado para absorber grandes cantidades de da\00f1o.', '/img/units/dragons/1. DTanque/enforcer-blade.png', U&'Drag\00f3n Tanque'),
(50, 90, 1, 5, 45, 250, 7, 'Heroe', 'El Berserker es una furia desatada en el campo de batalla, con ataques devastadores a costa de una defensa reducida.', '/img/units/dragons/2. DBerserker/clasher-blade.png', 'Berserker'),
(90, 75, 1, 5, 35, 280, 8, 'Heroe', U&'Con su alabarda m\00e1gica, el Guardi\00e1n equilibra ataque y defensa, protegiendo a sus aliados con su imponente presencia.', '/img/units/dragons/3. DAlabarda/warden.png', U&'Guardi\00e1n'),
(40, 110, 1, 4, 50, 200, 9, 'Heroe', U&'El Incinerador es un drag\00f3n de fuego que abrasa todo a su paso con llamas intensas y ataques explosivos.', '/img/units/dragons/4. DGris/burner.png', 'Incinerador'),
(30, 95, 1, 4, 65, 180, 10, 'Heroe', U&'Especializado en la magia y las runas, el Drag\00f3n Morado es un apoyo para su equipo y una amenaza para el contrario.', '/img/units/dragons/5. DMorado/blademaster.png', U&'Drag\00f3n Morado'),
(60, 50, 2, 3, 30, 300, 11, 'Heroe', 'El Troll Gigante es una bestia imponente con una fuerza descomunal.', '/img/units/trolls/1. TTanque/great-troll.png', 'Troll Gigante'),
(30, 100, 2, 2, 70, 150, 12, 'Heroe', U&'El Troll Asesino es r\00e1pido y letal, especializado en ataques furtivos.', '/img/units/trolls/2. TAssasin/whelp.png', 'Troll Asesino'),
(50, 80, 2, 3, 40, 200, 13, 'Heroe', 'El Troll Guerrero es un combatiente feroz con habilidades equilibradas.', '/img/units/trolls/3. TOfftanque/warrior.png', 'Troll Guerrero'),
(40, 90, 2, 2, 60, 180, 14, 'Heroe', U&'El Troll Certero es un experto en ataques a distancia con gran precisi\00f3n.', '/img/units/trolls/4. TRango/lobber.png', 'Troll Certero'),
(20, 70, 2, 4, 80, 160, 15, 'Heroe', U&'El Troll Cham\00e1n utiliza poderes m\00edsticos para apoyar a sus aliados.', '/img/units/trolls/5. TMago/shaman.png', U&'Troll Cham\00e1n'),
(70, 60, 3, 3, 30, 250, 16, 'Heroe', U&'El Esqueleto Corpulento es un guerrero resistente que puede soportar mucho da\00f1o.', '/img/units/skeletons/1. STanque/draug.png', 'Esqueleto Corpulento'),
(60, 80, 3, 4, 40, 220, 17, 'Heroe', 'El Esqueleto General lidera a sus tropas con una presencia intimidante.', '/img/units/skeletons/2. SGeneral/deathknight.png', 'Esqueleto General'),
(50, 90, 3, 4, 50, 200, 18, 'Heroe', U&'El Esqueleto a caballo es r\00e1pido y mortal en combate.', '/img/units/skeletons/3. SCaballero/boneknight-attack.png', 'Esqueleto a caballo'),
(40, 100, 3, 3, 60, 180, 19, 'Heroe', 'El Esqueleto Arquero es letal a larga distancia con su arco.', '/img/units/skeletons/4. SArquero/banebow-bow.png', 'Esqueleto Arquero'),
(30, 110, 3, 5, 70, 160, 20, 'Heroe', 'El Esqueleto mago utiliza poderosos hechizos para devastar a sus enemigos.', '/img/units/skeletons/5. SMago/ancient-lich.png', 'Esqueleto mago'),
(120, 200, 4, 6, 40, 400, 21, 'Heroe', 'Una criatura de leyenda con habilidades extraordinarias.', '/img/units/legendary/1. Legendaria/legendary-creature.png', 'Criatura Legendaria');        
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
    "PRECIO" INTEGER NOT NULL,
    "ID_OBJETO" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1 RESTART WITH 2) NOT NULL,
    "ID_UNIDAD" BIGINT,
    "DESCRIPCION" CHARACTER VARYING(255),
    "IMAGEN" CHARACTER VARYING(255),
    "NOMBRE" CHARACTER VARYING(255)
);     
ALTER TABLE "PUBLIC"."OBJETO" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_8A" PRIMARY KEY("ID_OBJETO");               
-- 1 +/- SELECT COUNT(*) FROM PUBLIC.OBJETO;  
INSERT INTO "PUBLIC"."OBJETO" VALUES
(3, 1, NULL, 'Un collar místico con el símbolo del ankh.', '/img/items/ankh-necklace.png', 'Collar Ankh'),
(2, 2, NULL, 'Un libro con conocimientos antiguos.', '/img/items/book5.png', 'Libro 5'),
(4, 3, NULL, 'Una espada envuelta en llamas.', '/img/items/flame-sword.png', 'Espada Llameante'),
(3, 4, NULL, 'Una lanza decorada con detalles intrincados.', '/img/items/spear-fancy.png', 'Lanza Elegante'),
(4, 5, NULL, 'Una espada bendecida con poder divino.', '/img/items/sword-holy.png', 'Espada Sagrada'),
(4, 6, NULL, 'Armadura hecha de oro puro, proporciona una gran protección.', '/img/items/armor-golden.png', 'Armadura Dorada'),
(2, 7, NULL, 'Un arco poderoso con gran precisión.', '/img/items/bow-2.png', 'Arco 2'),
(1, 8, NULL, 'Una flor rara con propiedades mágicas.', '/img/items/flower3.png', 'Flor Mágica'),
(2, 9, NULL, 'Una lanza ligera para lanzar.', '/img/items/spear-javelin.png', 'Lanza Jabalina'),
(2, 10, NULL, 'Una espada pequeña y rápida para golpes rápidos.', '/img/items/sword-short.png', 'Espada Corta'),
(2, 11, NULL, 'Armadura básica para protección.', '/img/items/armor.png', 'Armadura'),
(3, 12, NULL, 'Un arco hecho de cristal encantado.', '/img/items/bow-crystal-2.png', 'Arco Cristal 2'),
(4, 13, NULL, 'Un martillo grabado con runas antiguas.', '/img/items/hammer-runic.png', 'Martillo Rúnico'),
(2, 14, NULL, 'Una lanza estándar para combate.', '/img/items/spear.png', 'Lanza'),
(4, 15, NULL, 'Una espada que roba la energía vital de los enemigos.', '/img/items/sword-wraith.png', 'Espada Espectral'),
(3, 16, NULL, 'Un hacha grande para combate.', '/img/items/axe-2.png', 'Hacha de Batalla'),
(3, 17, NULL, 'Un arco hecho de cristal mágico.', '/img/items/bow-crystal.png', 'Arco de Cristal'),
(2, 18, NULL, 'Un collar hecho de huesos de criaturas míticas.', '/img/items/necklace-bone.png', 'Collar de Hueso'),
(4, 19, NULL, 'Un bastón mágico con poderosos hechizos.', '/img/items/staff-2.png', 'Bastón de Poder'),
(2, 20, NULL, 'Una espada básica para combate cuerpo a cuerpo.', '/img/items/sword.png', 'Espada');
CREATE MEMORY TABLE "PUBLIC"."PARTIDA"(
    "DUARCION_MIN" INTEGER NOT NULL,
    "ID_PARTIDA" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,
    "NOMBRE" CHARACTER VARYING(255)
);      
ALTER TABLE "PUBLIC"."PARTIDA" ADD CONSTRAINT "PUBLIC"."CONSTRAINT_F" PRIMARY KEY("ID_PARTIDA");              
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.PARTIDA; 
CREATE MEMORY TABLE "PUBLIC"."UNIDAD"(
    "ARMADURA_ACT" FLOAT(53),
    U&"DA\00d1O_ACT" FLOAT(53),
    "VELOCIDAD_ACT" FLOAT(53),
    "VIDA_ACT" FLOAT(53),
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
