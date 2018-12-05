-- MySQL Workbench Synchronization
-- Generated: 2018-11-21 13:41
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Samuel Koprda

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

ALTER TABLE `griddlers_db`.`hra` 
DROP FOREIGN KEY `fk_hra_krizovka`;

ALTER TABLE `griddlers_db`.`policko` 
DROP FOREIGN KEY `fk_policko_krizovka1`;

ALTER TABLE `griddlers_db`.`policko_hry` 
DROP FOREIGN KEY `fk_policko_hry_hra1`;

ALTER TABLE `griddlers_db`.`legenda` 
DROP FOREIGN KEY `fk_legenda_krizovka1`;

ALTER TABLE `griddlers_db`.`policko` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `griddlers_db`.`hra` 
ADD CONSTRAINT `fk_hra_krizovka`
  FOREIGN KEY (`krizovka_id`)
  REFERENCES `griddlers_db`.`krizovka` (`id`) 
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `griddlers_db`.`policko` 
ADD CONSTRAINT `fk_policko_krizovka1`
  FOREIGN KEY (`krizovka_id`)
  REFERENCES `griddlers_db`.`krizovka` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `griddlers_db`.`policko_hry` 
ADD CONSTRAINT `fk_policko_hry_hra1`
  FOREIGN KEY (`hra_id`)
  REFERENCES `griddlers_db`.`hra` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `griddlers_db`.`legenda` 
ADD CONSTRAINT `fk_legenda_krizovka1`
  FOREIGN KEY (`krizovka_id`)
  REFERENCES `griddlers_db`.`krizovka` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
