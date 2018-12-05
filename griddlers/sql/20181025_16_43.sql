-- MySQL Workbench Synchronization
-- Generated: 2018-10-25 16:43
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

ALTER TABLE `griddlers_db`.`hra` 
CHANGE COLUMN `legenda_h` `legenda_h` VARCHAR(1000) NULL DEFAULT NULL ,
CHANGE COLUMN `legenda_l` `legenda_l` VARCHAR(1000) NULL DEFAULT NULL ;

ALTER TABLE `griddlers_db`.`policko` 
CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT ;

ALTER TABLE `griddlers_db`.`hra` 
ADD CONSTRAINT `fk_hra_krizovka`
  FOREIGN KEY (`krizovka_id`)
  REFERENCES `griddlers_db`.`krizovka` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `griddlers_db`.`policko` 
ADD CONSTRAINT `fk_policko_krizovka1`
  FOREIGN KEY (`krizovka_id`)
  REFERENCES `griddlers_db`.`krizovka` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `griddlers_db`.`policko_hry` 
ADD CONSTRAINT `fk_policko_hry_hra1`
  FOREIGN KEY (`hra_id`)
  REFERENCES `griddlers_db`.`hra` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
