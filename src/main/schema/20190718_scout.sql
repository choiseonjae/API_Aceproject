-- scoutingReport를 위한 스키마들
CREATE TABLE IF NOT EXISTS account (
 team_id INT NOT NULL,
 ap INT NOT NULL DEFAULT 0,
 crt_date DATETIME NOT NULL,
 upd_date DATETIME NOT NULL,
 PRIMARY KEY (team_id),
 INDEX fk$team$account$team_id (team_id ASC) VISIBLE,
 CONSTRAINT fk_account_team1
   FOREIGN KEY (team_id)
   REFERENCES team (team_id)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS team (
 team_id INT NOT NULL AUTO_INCREMENT,
 team_name VARCHAR(45) NOT NULL,
 crt_date DATETIME NOT NULL,
 upd_date DATETIME NOT NULL,
 PRIMARY KEY (team_id))
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS team_sr_slot (
 team_id INT NOT NULL,
 slot_no INT NOT NULL,
 player_id INT NOT NULL,
 contract_yn CHAR(1) NOT NULL DEFAULT ‘N’,
 crt_date DATETIME NOT NULL,
 upd_date DATETIME NOT NULL,
 PRIMARY KEY (team_id, slot_no),
 INDEX fk$player$team_sr_slot$player_id (player_id ASC) VISIBLE,
 CONSTRAINT fk_slot&team_id
   FOREIGN KEY (team_id)
   REFERENCES team (team_id)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION,
 CONSTRAINT fk_slot$player_id
   FOREIGN KEY (player_id)
   REFERENCES player (player_id)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS team_player (
 team_id INT NOT NULL,
 player_id INT NOT NULL,
 level INT NOT NULL,
 exp INT NOT NULL,
 crt_date DATETIME NOT NULL,
 upd_date DATETIME NOT NULL,
 PRIMARY KEY (team_id, player_id),
 INDEX fk$player$team_player$player_id (player_id ASC) VISIBLE,
 CONSTRAINT fk_team_player$team_id
   FOREIGN KEY (team_id)
   REFERENCES team (team_id)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION,
 CONSTRAINT fk_team_player$player_id
   FOREIGN KEY (player_id)
   REFERENCES player (player_id)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS player (
 player_id INT NOT NULL,
 person_id INT NOT NULL,
 team_code VARCHAR(10) NOT NULL,
 cost INT NOT NULL,
 crt_date DATETIME NOT NULL,
 upd_date DATETIME NOT NULL,
 PRIMARY KEY (player_id),
 INDEX fk$person$player$person_id (person_id ASC) VISIBLE,
 CONSTRAINT fk_player_person1
   FOREIGN KEY (person_id)
   REFERENCES person (person_id)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION)
ENGINE = InnoDB

CREATE TABLE IF NOT EXISTS person (
 person_id INT NOT NULL,
 name VARCHAR(45) NOT NULL,
 crt_date DATETIME NOT NULL,
 upd_date DATETIME NOT NULL,
 PRIMARY KEY (person_id))
ENGINE = InnoDB

-- 테스트를 위한 임시세팅
-- person 기초 데이터 세팅 
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('1', '이승엽', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('2', '김현수', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('3', '류현진', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('4', '황재균', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('5', '강민호', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('6', '최정', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('7', '이대호', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('8', '손아섭', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('9', '김원중', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('10', '민병헌', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('11', '손승락', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`person` (`person_id`, `name`, `crt_date`, `upd_date`) VALUES ('12', '전준우', '2019-07-16 17:29:40', '2019-07-16 17:29:40');

-- player 기초 데이터 세팅
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('1', '1', '삼성 라이온즈', '1', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('2', '2', '두산 베어스', '2', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('3', '2', '볼티모어 오리올스', '4', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('4', '2', '필라델피아 필리스', '3', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('5', '2', 'LG 트윈스', '5', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('6', '3', '한화 이글스', '6', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('7', '3', '로스엔젤레스 다저스', '7', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('8', '4', '현대 유니콘스', '8', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('9', '4', '넥센 히어로즈', '8', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('10', '4', '롯데 자이언츠', '6', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('11', '5', '롯데 자이언츠', '5', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('12', '5', '삼성 라이온즈', '4', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('13', '6', 'SK 와이번스', '2', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('14', '7', '롯데 자이언츠', '4', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('15', '8', '롯데 자이언츠', '1', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('16', '9', '롯데 자이언츠', '3', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('17', '10', '롯데 자이언츠', '2', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('18', '11', '롯데 자이언츠', '5', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('19', '12', '롯데 자이언츠', '4', '2019-07-16 17:29:40', '2019-07-16 17:29:40');
INSERT INTO `test_db`.`player` (`player_id`, `person_id`, `team_code`, `cost`, `crt_date`, `upd_date`) VALUES ('20', '12', '롯데 자이언츠', '6', '2019-07-16 17:29:40', '2019-07-16 17:29:40');

-- account 기초 데이터 세팅
INSERT INTO `test_db`.`account` (`team_id`, `ap`, `crt_date`, `upd_date`) VALUES ('1', '1150', '2019-07-16 17:29:40', '2019-07-19 18:06:29');

-- team 기초 데이터 세팅
INSERT INTO `test_db`.`team` (`team_id`, `team_name`, `last_refresh_time`, `crt_date`, `upd_date`) VALUES ('1', '경기대학', '2019-07-19 16:49:30', '2019-07-16 17:29:40', '2019-07-19 16:49:29');

-- team player 기초 데이터 세팅 ( 없어도 됨 )
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '1', '3', '1', '2019-07-19 11:19:22', '2019-07-19 16:50:53');
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '4', '1', '1', '2019-07-19 11:19:23', '2019-07-19 11:19:23');
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '5', '2', '1', '2019-07-19 15:33:06', '2019-07-19 16:12:41');
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '6', '1', '1', '2019-07-19 16:52:15', '2019-07-19 16:52:15');
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '7', '2', '1', '2019-07-19 11:19:22', '2019-07-19 11:19:23');
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '9', '1', '1', '2019-07-19 15:31:48', '2019-07-19 15:31:48');
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '11', '2', '1', '2019-07-19 11:19:22', '2019-07-19 11:19:22');
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '12', '2', '1', '2019-07-19 11:19:22', '2019-07-19 16:03:55');
INSERT INTO `test_db`.`team_player` (`team_id`, `player_id`, `level`, `exp`, `crt_date`, `upd_date`) VALUES ('1', '13', '3', '1', '2019-07-19 11:19:22', '2019-07-19 11:19:23');

-- team_sr_slot 기초 데이터 세팅 ( 없어도 됨 )
INSERT INTO `test_db`.`team_sr_slot` (`team_id`, `slot_no`, `player_id`, `contract_yn`, `crt_date`, `upd_date`) VALUES ('1', '1', '1', 'Y', '2019-07-18 18:21:32', '2019-07-19 16:50:53');
INSERT INTO `test_db`.`team_sr_slot` (`team_id`, `slot_no`, `player_id`, `contract_yn`, `crt_date`, `upd_date`) VALUES ('1', '2', '4', 'N', '2019-07-18 18:21:32', '2019-07-19 16:49:29');
INSERT INTO `test_db`.`team_sr_slot` (`team_id`, `slot_no`, `player_id`, `contract_yn`, `crt_date`, `upd_date`) VALUES ('1', '3', '17', 'N', '2019-07-18 18:21:32', '2019-07-19 16:49:29');
INSERT INTO `test_db`.`team_sr_slot` (`team_id`, `slot_no`, `player_id`, `contract_yn`, `crt_date`, `upd_date`) VALUES ('1', '4', '6', 'Y', '2019-07-18 18:21:32', '2019-07-19 16:52:15');


