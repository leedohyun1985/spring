-- --------------------------------------------------------
-- 호스트:                          leedohyun.asuscomm.com
-- 서버 버전:                        10.5.5-MariaDB - MariaDB Server
-- 서버 OS:                        Linux
-- HeidiSQL 버전:                  11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- spring 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `spring` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `spring`;

-- 테이블 spring.authorities 구조 내보내기
CREATE TABLE IF NOT EXISTS `authorities` (
  `order_no` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '순번',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '사용자고유번호',
  `user_authority` smallint(5) unsigned NOT NULL COMMENT '사용자권한',
  `c_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '작성자고유번호',
  `c_timestamp` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `m_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '수정자고유번호',
  `m_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`order_no`),
  KEY `FK_authorities_users_user_id` (`user_id`),
  CONSTRAINT `FK_authorities_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 권한';

-- 테이블 데이터 spring.authorities:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;

-- 테이블 spring.authorities_codes 구조 내보내기
CREATE TABLE IF NOT EXISTS `authorities_codes` (
  `authority_code` int(5) unsigned NOT NULL DEFAULT 0 COMMENT '사용자상태코드',
  `authority_name` varchar(100) NOT NULL COMMENT '사용자상태코드명',
  `authority_desc` varchar(100) NOT NULL COMMENT '사용자상태코드설명',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '사용가능여부',
  `c_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '작성자고유번호',
  `c_timestamp` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `m_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '수정자고유번호',
  `m_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`authority_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 권한 코드';

-- 테이블 데이터 spring.authorities_codes:~2 rows (대략적) 내보내기
/*!40000 ALTER TABLE `authorities_codes` DISABLE KEYS */;
INSERT INTO `authorities_codes` (`authority_code`, `authority_name`, `authority_desc`, `enabled`, `c_user_id`, `c_timestamp`, `m_user_id`, `m_timestamp`) VALUES
	(0, 'ROLE_ADMIN', '관리자', 1, 0, '2020-09-12 12:06:47', 0, '2020-09-12 12:06:47'),
	(1, 'ROLE_USER', '사용자', 1, 0, '2020-09-12 12:07:04', 0, '2020-09-12 12:07:04');
/*!40000 ALTER TABLE `authorities_codes` ENABLE KEYS */;

-- 테이블 spring.i18n_locales 구조 내보내기
CREATE TABLE IF NOT EXISTS `i18n_locales` (
  `locale_no` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '순번',
  `country` varchar(20) DEFAULT NULL COMMENT '국가',
  `language` varchar(20) DEFAULT NULL COMMENT '언어',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '사용가능여부',
  `c_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '작성자고유번호',
  `c_timestamp` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `m_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '수정자고유번호',
  `m_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`locale_no`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='국제화 로캘';

-- 테이블 데이터 spring.i18n_locales:~6 rows (대략적) 내보내기
/*!40000 ALTER TABLE `i18n_locales` DISABLE KEYS */;
INSERT INTO `i18n_locales` (`locale_no`, `country`, `language`, `enabled`, `c_user_id`, `c_timestamp`, `m_user_id`, `m_timestamp`) VALUES
	(1, 'KR', 'ko', 1, 0, '2020-09-15 16:52:34', 0, '2020-09-15 16:52:34'),
	(2, 'US', 'en', 1, 0, '2020-09-15 16:52:34', 0, '2020-09-15 16:52:34'),
	(3, 'CN', 'zh', 1, 0, '2020-09-15 16:52:34', 0, '2020-09-15 16:52:34'),
	(4, 'TW', 'zh', 1, 0, '2020-09-15 16:52:34', 0, '2020-09-15 16:52:34'),
	(5, 'HK', 'zh', 1, 0, '2020-09-15 16:52:34', 0, '2020-09-15 16:52:34'),
	(6, 'JP', 'ja', 1, 0, '2020-09-15 16:52:34', 0, '2020-09-15 16:52:34');
/*!40000 ALTER TABLE `i18n_locales` ENABLE KEYS */;

-- 테이블 spring.i18n_properties 구조 내보내기
CREATE TABLE IF NOT EXISTS `i18n_properties` (
  `order_no` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '순번',
  `locale_no` bigint(20) unsigned NOT NULL COMMENT '로캘 고유번호',
  `code` varchar(100) DEFAULT NULL COMMENT '메시지코드',
  `message` varchar(100) DEFAULT NULL COMMENT '메시지',
  `c_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '작성자고유번호',
  `c_timestamp` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `m_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '수정자고유번호',
  `m_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`order_no`),
  KEY `i18n_properties_FK` (`locale_no`),
  CONSTRAINT `i18n_properties_FK` FOREIGN KEY (`locale_no`) REFERENCES `i18n_locales` (`locale_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='국제화 프로퍼티';

-- 테이블 데이터 spring.i18n_properties:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `i18n_properties` DISABLE KEYS */;
/*!40000 ALTER TABLE `i18n_properties` ENABLE KEYS */;

-- 테이블 spring.persistent_logins 구조 내보내기
CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `series` varchar(64) NOT NULL COMMENT '시리즈',
  `username` varchar(64) NOT NULL COMMENT '사용자명',
  `token` varchar(64) NOT NULL COMMENT '토큰',
  `last_used` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '마지막사용일시',
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='자동 로그인 (입력갱신삭제금지)';

-- 테이블 데이터 spring.persistent_logins:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;

-- 테이블 spring.users 구조 내보내기
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '사용자고유번호',
  `user_email` varchar(50) NOT NULL COMMENT '사용자이메일',
  `user_name` varchar(100) NOT NULL COMMENT '사용자명',
  `password` varchar(60) NOT NULL COMMENT '사용자패스워드',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '사용가능여부',
  `email_cnt` tinyint(4) NOT NULL DEFAULT 0 COMMENT '이메일인증횟수',
  `c_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '작성자고유번호',
  `c_timestamp` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `m_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '수정자고유번호',
  `m_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `user_email` (`user_email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 정보';

-- 테이블 데이터 spring.users:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- 테이블 spring.user_emal_confirm_codes 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_emal_confirm_codes` (
  `order_no` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '순번',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '사용자고유번호',
  `email_confirm_code` varchar(50) NOT NULL COMMENT '이메일인증코드',
  `c_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '작성자고유번호',
  `c_timestamp` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `m_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '수정자고유번호',
  `m_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`order_no`),
  KEY `FK_user_emal_confirm_code_users` (`user_id`),
  CONSTRAINT `FK_user_emal_confirm_code_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='이메일 인증코드';

-- 테이블 데이터 spring.user_emal_confirm_codes:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `user_emal_confirm_codes` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_emal_confirm_codes` ENABLE KEYS */;

-- 테이블 spring.user_status 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_status` (
  `order_no` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '순번',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '사용자고유번호',
  `user_status_code` int(5) unsigned NOT NULL DEFAULT 0 COMMENT '사용자상태코드',
  `c_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '작성자고유번호',
  `c_timestamp` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `m_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '수정자고유번호',
  `m_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`order_no`),
  KEY `FK_user_status_users_user_id` (`user_id`),
  KEY `FK_user_status_user_status_code_user_status_code` (`user_status_code`),
  CONSTRAINT `FK_user_status_user_status_code_user_status_code` FOREIGN KEY (`user_status_code`) REFERENCES `user_status_codes` (`user_status_code`),
  CONSTRAINT `FK_user_status_users_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 상태';

-- 테이블 데이터 spring.user_status:~0 rows (대략적) 내보내기
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;

-- 테이블 spring.user_status_codes 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_status_codes` (
  `user_status_code` int(5) unsigned NOT NULL COMMENT '사용자상태코드',
  `user_status_name` varchar(100) NOT NULL COMMENT '사용자상태코드명',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '사용가능여부',
  `c_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '작성자고유번호',
  `c_timestamp` timestamp NOT NULL DEFAULT current_timestamp() COMMENT '작성일시',
  `m_user_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '수정자고유번호',
  `m_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '수정일시',
  PRIMARY KEY (`user_status_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 상태코드';

-- 테이블 데이터 spring.user_status_codes:~6 rows (대략적) 내보내기
/*!40000 ALTER TABLE `user_status_codes` DISABLE KEYS */;
INSERT INTO `user_status_codes` (`user_status_code`, `user_status_name`, `enabled`, `c_user_id`, `c_timestamp`, `m_user_id`, `m_timestamp`) VALUES
	(1, '정상', 1, 0, '2020-09-12 12:07:45', 0, '2020-09-12 12:07:45'),
	(2, '이메일인증대기', 1, 0, '2020-09-12 12:07:52', 0, '2020-09-12 12:07:52'),
	(3, '계정만료', 1, 0, '2020-09-12 12:08:03', 0, '2020-09-12 12:08:03'),
	(4, '계정잠금', 1, 0, '2020-09-12 12:08:11', 0, '2020-09-12 12:08:11'),
	(5, '패스워드만료', 1, 0, '2020-09-12 12:08:20', 0, '2020-09-12 12:08:20'),
	(6, '탈퇴', 1, 0, '2020-09-12 12:08:28', 0, '2020-09-12 12:08:28');
/*!40000 ALTER TABLE `user_status_codes` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
