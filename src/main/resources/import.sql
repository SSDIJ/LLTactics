-- insert admin (username a, password aa)
INSERT INTO IWUser (id, enabled, roles, username, password)
VALUES (1, TRUE, 'ADMIN,USER', 'a',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
INSERT INTO IWUser (id, enabled, roles, username, password)
VALUES (2, TRUE, 'USER', 'b',
    '{bcrypt}$2a$10$2BpNTbrsarbHjNsUWgzfNubJqBRf.0Vz9924nRSHBqlbPKerkgX.W');
 
INSERT INTO "PUBLIC"."HEROE" (vida, armadura, faccion, daño, velocidad, descripcion, imagen, nombre, price) VALUES
(50, 75, 0, 100, 25, U&'Es la primera l\00ednea de defensa.', '/img/units/humans/1. Tanque/heavyinfantry.png', 'Tanque', 3),
(80, 70, 0, 40, 200, 'Un estratega nato en el campo de batalla.', '/img/units/humans/2. General/general.png', 'General de Guerra', 4),
(60, 85, 0, 50, 180, U&'\00c1gil y feroz en combate.', '/img/units/humans/3. Caballero/knight.png', 'Caballero', 3),
(30, 95, 0, 70, 120, U&'Con una punter\00eda letal.', '/img/units/humans/4. Arquero/longbowman-bow-attack-1.png', 'Arquero', 2),
(20, 110, 0, 60, 140, 'Un maestro de la magia arcana.', '/img/units/humans/5. Mago/white-mage.png', 'Mago', 5),
(120, 40, 1, 20, 350, U&'El Drag\00f3n Tanque es una fortaleza viviente con una armadura impenetrable, dise\00f1ado para absorber grandes cantidades de da\00f1o.', '/img/units/dragons/1. DTanque/enforcer-blade.png', U&'Drag\00f3n Tanque', 6),
(50, 90, 1, 45, 250, 'El Berserker es una furia desatada en el campo de batalla, con ataques devastadores a costa de una defensa reducida.', '/img/units/dragons/2. DBerserker/clasher-blade.png', 'Berserker', 5),
(90, 75, 1, 35, 280, U&'Con su alabarda m\00e1gica, el Guardi\00e1n equilibra ataque y defensa, protegiendo a sus aliados con su imponente presencia.', '/img/units/dragons/3. DAlabarda/warden.png', U&'Guardi\00e1n', 5),
(40, 110, 1, 50, 200,  U&'El Incinerador es un drag\00f3n de fuego que abrasa todo a su paso con llamas intensas y ataques explosivos.', '/img/units/dragons/4. DGris/burner.png', 'Incinerador', 4),
(30, 95, 1, 65, 180,  U&'Especializado en la magia y las runas, el Drag\00f3n Morado es un apoyo para su equipo y una amenaza para el contrario.', '/img/units/dragons/5. DMorado/blademaster.png', U&'Drag\00f3n Morado', 4),
(60, 50, 2, 30, 300,  'El Troll Gigante es una bestia imponente con una fuerza descomunal.', '/img/units/trolls/1. TTanque/great-troll.png', 'Troll Gigante', 3),
(30, 100, 2, 70, 150, U&'El Troll Asesino es r\00e1pido y letal, especializado en ataques furtivos.', '/img/units/trolls/2. TAssasin/whelp.png', 'Troll Asesino', 2),
(50, 80, 2, 40, 200, 'El Troll Guerrero es un combatiente feroz con habilidades equilibradas.', '/img/units/trolls/3. TOfftanque/warrior.png', 'Troll Guerrero', 3),
(40, 90, 2, 60, 180, U&'El Troll Certero es un experto en ataques a distancia con gran precisi\00f3n.', '/img/units/trolls/4. TRango/lobber.png', 'Troll Certero', 2),
(20, 70, 2, 80, 160, U&'El Troll Cham\00e1n utiliza poderes m\00edsticos para apoyar a sus aliados.', '/img/units/trolls/5. TMago/shaman.png', U&'Troll Cham\00e1n', 4),
(70, 60, 3, 30, 250, U&'El Esqueleto Corpulento es un guerrero resistente que puede soportar mucho da\00f1o.', '/img/units/skeletons/1. STanque/draug.png', 'Esqueleto Corpulento', 3),
(60, 80, 3, 40, 220, 'El Esqueleto General lidera a sus tropas con una presencia intimidante.', '/img/units/skeletons/2. SGeneral/deathknight.png', 'Esqueleto General', 4),
(50, 90, 3, 50, 200, U&'El Esqueleto a caballo es r\00e1pido y mortal en combate.', '/img/units/skeletons/3. SCaballero/boneknight-attack.png', 'Esqueleto a caballo', 4),
(40, 100, 3, 60, 180, 'El Esqueleto Arquero es letal a larga distancia con su arco.', '/img/units/skeletons/4. SArquero/banebow-bow.png', 'Esqueleto Arquero', 3),
(30, 110, 3, 70, 160, 'El Esqueleto mago utiliza poderosos hechizos para devastar a sus enemigos.', '/img/units/skeletons/5. SMago/ancient-lich.png', 'Esqueleto mago', 5),
(120, 200, 4, 40, 400, 'Una criatura de leyenda con habilidades extraordinarias.', '/img/units/legendary/fireghost/fireghost.png', 'Criatura Legendaria', 6);


INSERT INTO "PUBLIC"."ITEM" (imageurl, name, description, price) VALUES
('/img/items/ankh-necklace.png', 'Collar Ankh', 'Un collar místico con el símbolo del ankh.', 3),
('/img/items/book5.png', 'Libro 5', 'Un libro con conocimientos antiguos.', 2),
('/img/items/flame-sword.png', 'Espada Llameante', 'Una espada envuelta en llamas.', 4),
('/img/items/spear-fancy.png', 'Lanza Elegante', 'Una lanza decorada con detalles intrincados.', 3),
('/img/items/sword-holy.png', 'Espada Sagrada', 'Una espada bendecida con poder divino.', 4),
('/img/items/armor-golden.png', 'Armadura Dorada', 'Armadura hecha de oro puro, proporciona una gran protección.', 4),
('/img/items/bow-2.png', 'Arco 2', 'Un arco poderoso con gran precisión.', 2),
('/img/items/flower3.png', 'Flor Mágica', 'Una flor rara con propiedades mágicas.', 1),
('/img/items/spear-javelin.png', 'Lanza Jabalina', 'Una lanza ligera para lanzar.', 2),
('/img/items/sword-short.png', 'Espada Corta', 'Una espada pequeña y rápida para golpes rápidos.', 2),
('/img/items/armor.png', 'Armadura', 'Armadura básica para protección.', 2),
('/img/items/bow-crystal-2.png', 'Arco Cristal 2', 'Un arco hecho de cristal encantado.', 3),
('/img/items/hammer-runic.png', 'Martillo Rúnico', 'Un martillo grabado con runas antiguas.', 4),
('/img/items/spear.png', 'Lanza', 'Una lanza estándar para combate.', 2),
('/img/items/sword-wraith.png', 'Espada Espectral', 'Una espada que roba la energía vital de los enemigos.', 4),
('/img/items/axe-2.png', 'Hacha de Batalla', 'Un hacha grande para combate.', 3),
('/img/items/bow-crystal.png', 'Arco de Cristal', 'Un arco hecho de cristal mágico.', 3),
('/img/items/necklace-bone.png', 'Collar de Hueso', 'Un collar hecho de huesos de criaturas míticas.', 2),
('/img/items/staff-2.png', 'Bastón de Poder', 'Un bastón mágico con poderosos hechizos.', 4),
('/img/items/sword.png', 'Espada', 'Una espada básica para combate cuerpo a cuerpo.', 2),
('/img/items/axe.png', 'Hacha', 'Un hacha fuerte para cortar y combate.', 3),
('/img/items/bow-elven-2.png', 'Arco Élfico 2', 'Un arco creado por los elfos con precisión insuperable.', 4),
('/img/items/necklace-stone.png', 'Collar de Piedra', 'Un collar hecho de piedras raras.', 2),
('/img/items/staff-druid.png', 'Bastón Druídico', 'Un bastón usado por los druidas para controlar la naturaleza.', 4),
('/img/items/talisman-ankh.png', 'Talismán Ankh', 'Un talismán con forma de ankh para protección.', 3),
('/img/items/ball-blue.png', 'Bola Azul', 'Una bola mágica de energía azul.', 1),
('/img/items/bow-elven.png', 'Arco Élfico', 'Un arco elegante creado por los elfos, ligero y rápido.', 3),
('/img/items/potion-blue.png', 'Poción Azul', 'Una poción que restaura salud.', 1),
('/img/items/staff-magic-2.png', 'Bastón Mágico 2', 'Un bastón imbuido con poder mágico.', 4),
('/img/items/talisman-bone.png', 'Talismán de Hueso', 'Un talismán hecho de hueso con hechizos protectores.', 2),
('/img/items/ball-green.png', 'Bola Verde', 'Una bola mágica que otorga sabiduría.', 1),
('/img/items/crossbow.png', 'Ballesta', 'Una ballesta con gran precisión.', 3),
('/img/items/potion-green.png', 'Poción Verde', 'Una poción que aumenta la fuerza.', 1),
('/img/items/staff-magic.png', 'Bastón Mágico', 'Un bastón poderoso para lanzar hechizos.', 4),
('/img/items/talisman-stone.png', 'Talismán de Piedra', 'Un talismán hecho de piedra para protección.', 2),
('/img/items/ball-magenta.png', 'Bola Magenta', 'Una bola llena de energía mágica caótica.', 1),
('/img/items/dagger-poison-bare.png', 'Daga envenenada', 'Una daga cubierta con veneno mortal.', 2),
('/img/items/potion-poison.png', 'Poción Venenosa', 'Una poción que envenena a los enemigos.', 2),
('/img/items/staff.png', 'Bastón', 'Un bastón básico para un mago o hechicero.', 2),
('/img/items/bones.png', 'Huesos', 'Huesos de una criatura legendaria.', 1),
('/img/items/dagger-poison.png', 'Daga Venenosa', 'Una daga con una hoja afilada bañada en veneno.', 3),
('/img/items/potion-red.png', 'Poción Roja', 'Una poción que aumenta la velocidad y agilidad.', 2),
('/img/items/storm-trident-2.png', 'Tridente de Tormenta 2', 'Un tridente que controla el clima.', 4),
('/img/items/book1.png', 'Libro 1', 'Un libro de poderosos hechizos antiguos.', 3),
('/img/items/dagger.png', 'Daga', 'Una pequeña hoja afilada para golpes rápidos.', 2),
('/img/items/potion-yellow.png', 'Poción Amarilla', 'Una poción que aumenta la defensa.', 1),
('/img/items/storm-trident.png', 'Tridente de Tormenta', 'Un tridente poderoso que invoca tormentas.', 4),
('/img/items/flame-sword-bare.png', 'Espada Llameante Básica', 'Una espada envuelta en llamas que puede quemar todo.', 3),
('/img/items/spear-fancy-2.png', 'Lanza Elegante 2', 'Una lanza aún más decorada y poderosa.', 3),
('/img/items/sword-2.png', 'Espada 2', 'Una espada afilada utilizada por guerreros.', 2);



-- start id numbering from a value that is larger than any assigned above
ALTER SEQUENCE "PUBLIC"."GEN" RESTART WITH 1024;
