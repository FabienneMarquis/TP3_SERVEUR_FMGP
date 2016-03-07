
CREATE SCHEMA IF NOT EXISTS `pr_tp3`;
use `pr_tp3`;

DROP TABLE IF EXISTS `clients`;
CREATE TABLE `clients` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `nom` VARCHAR(255) NOT NULL,
    `prenom` VARCHAR(255) NOT NULL,
    `telephone` INT (11) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `chambres`;
CREATE TABLE `chambres` (
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `nom` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `reservations`;
CREATE TABLE `reservations`(
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `id_client` int(11) NOT NULL,
    `id_chambre` int(11) NOT NULL,
    `checkin` DATE NOT NULL,
    `checkout` DATE NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_reservation_client` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id`),
    CONSTRAINT `fk_reservation_chambre` FOREIGN KEY (`id_chambre`) REFERENCES `chambres` (`id`)
);

DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees`(
    `id` int (11) NOT NULL AUTO_INCREMENT,
    `nom` VARCHAR(255) NOT NULL,
    `prenom` VARCHAR(255) NOT NULL,
    `mot_de_passe` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `chambres` VALUES (1,"Roche","Lit de pierre"),(2,"Paille","Lit de paille");
INSERT INTO `clients` VALUES (1,"Patate","Bob",111222333),(2,"Gros-nez","Bobbette",222334121);
INSERT INTO `reservations` VALUES (1,1,1,"2016-03-05","2016-03-06"),(2,2,2,"2016-03-05","2016-03-07");