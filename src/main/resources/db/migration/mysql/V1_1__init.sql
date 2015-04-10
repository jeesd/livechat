-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Hostiteľ: localhost
-- Vygenerované: So 28.Mar 2015, 11:40
-- Verzia serveru: 5.5.24-log
-- Verzia PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databáza: `mylivedata`
--
CREATE DATABASE IF NOT EXISTS `mylivedata` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `mylivedata`;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `accounts`
--

CREATE TABLE IF NOT EXISTS `accounts` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT NULL,
  `billing_email` varchar(255) DEFAULT NULL,
  `company_name` varchar(500) DEFAULT NULL,
  `street_adrress_line_1` varchar(1000) DEFAULT NULL,
  `street_address_line_2` varchar(1000) DEFAULT NULL,
  `City` varchar(500) DEFAULT NULL,
  `state_province` varchar(500) DEFAULT NULL,
  `zip` varchar(20) DEFAULT NULL,
  `country` varchar(500) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `registration_code` varchar(255) DEFAULT NULL,
  `is_veriefied` tinyint(1) NOT NULL DEFAULT '0',
  `account_identity` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `registration_code` (`registration_code`,`is_veriefied`),
  KEY `account_user_id_idx` (`owner_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=45 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `assoc_group_role`
--

CREATE TABLE IF NOT EXISTS `assoc_group_role` (
  `role_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`group_id`),
  KEY `to_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `assoc_user_domain`
--

CREATE TABLE IF NOT EXISTS `assoc_user_domain` (
  `user_id` int(11) NOT NULL,
  `domain_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`domain_id`),
  KEY `udassoc_to_domain` (`domain_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `assoc_user_groups`
--

CREATE TABLE IF NOT EXISTS `assoc_user_groups` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`),
  KEY `aug_to_group` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `conversation`
--

CREATE TABLE IF NOT EXISTS `conversation` (
  `conversation_id` int(11) NOT NULL AUTO_INCREMENT,
  `chat_id` int(11) NOT NULL,
  `message_by` enum('user','visitor') DEFAULT NULL,
  `message_text` longtext CHARACTER SET latin1,
  PRIMARY KEY (`conversation_id`),
  KEY `conversation_to_chat` (`chat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `default_layouts`
--

CREATE TABLE IF NOT EXISTS `default_layouts` (
  `layout_id` int(11) NOT NULL AUTO_INCREMENT,
  `html` longtext CHARACTER SET latin1,
  `css` text CHARACTER SET latin1,
  `name` varchar(50) DEFAULT NULL,
  `fragment` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`layout_id`),
  UNIQUE KEY `Name_Fragment_IDX` (`name`,`fragment`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `departments`
--

CREATE TABLE IF NOT EXISTS `departments` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `domain_id` int(11) DEFAULT NULL,
  `key_name` varchar(20) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`department_id`),
  KEY `department_to_account` (`account_id`),
  KEY `department_to_domain` (`domain_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `domains`
--

CREATE TABLE IF NOT EXISTS `domains` (
  `domain_id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(255) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `account_id` int(11) NOT NULL,
  `visible_offline` enum('Yes','No') DEFAULT 'Yes',
  PRIMARY KEY (`domain_id`),
  KEY `domain_to_account` (`account_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `group_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `ip2location_db11`
--

CREATE TABLE IF NOT EXISTS `ip2location_db11` (
  `ip_from` int(10) unsigned NOT NULL DEFAULT '0',
  `ip_to` int(10) unsigned NOT NULL DEFAULT '0',
  `country_code` char(2) COLLATE utf8_bin DEFAULT NULL,
  `country_name` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `region_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `city_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `zip_code` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `time_zone` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ip_from`,`ip_to`),
  KEY `idx_ip_from` (`ip_from`),
  KEY `idx_ip_to` (`ip_to`),
  KEY `idx_ip_from_to` (`ip_from`,`ip_to`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `message_resource`
--

CREATE TABLE IF NOT EXISTS `message_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lang_iso_code` varchar(2) DEFAULT NULL,
  `text_code` varchar(100) DEFAULT NULL,
  `text_value` varchar(1000) DEFAULT NULL,
  `coutry_iso_code` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=60 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `online_users`
--

CREATE TABLE IF NOT EXISTS `online_users` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `is_online` int(11) NOT NULL DEFAULT '0',
  `department_id` int(11) DEFAULT NULL,
  `is_chat_history` int(11) NOT NULL DEFAULT '0',
  `is_in_chat` int(11) NOT NULL DEFAULT '0',
  `is_operator` int(11) NOT NULL DEFAULT '0',
  `session_id` int(11) DEFAULT NULL,
  `is_http_online` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `unique_user_id` (`user_id`),
  KEY `online_users_ibfk_2` (`department_id`),
  KEY `online_user_to_session` (`session_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `persistent_logins`
--

CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `roles`
--

CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `userconnection`
--

CREATE TABLE IF NOT EXISTS `userconnection` (
  `userId` int(11) NOT NULL,
  `providerId` varchar(100) NOT NULL,
  `providerUserId` varchar(50) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(255) DEFAULT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  `accessToken` varchar(200) DEFAULT NULL,
  `secret` varchar(150) DEFAULT NULL,
  `refreshToken` varchar(150) DEFAULT NULL,
  `expireTime` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) DEFAULT NULL,
  `surrname` varchar(500) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(1000) DEFAULT NULL,
  `registration` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_login` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_ip` varchar(50) DEFAULT NULL,
  `identification_hash` varchar(255) DEFAULT NULL,
  `account_id` int(11) NOT NULL,
  `is_credentials_non_expired` enum('true','false') NOT NULL DEFAULT 'true',
  `image_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `unique_identification_hash` (`identification_hash`),
  KEY `user_to_account` (`account_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2541 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `users_chats`
--

CREATE TABLE IF NOT EXISTS `users_chats` (
  `chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `in_chat_with_user_id` int(11) NOT NULL,
  `initialized_by` enum('user','visitor') NOT NULL,
  `start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `satisfaction` int(11) DEFAULT NULL,
  PRIMARY KEY (`chat_id`),
  KEY `chat_to_user` (`user_id`),
  KEY `chat_to_visitor` (`in_chat_with_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `user_geo_data`
--

CREATE TABLE IF NOT EXISTS `user_geo_data` (
  `vgd_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `COUNTRY_CODE` varchar(2) DEFAULT NULL,
  `COUNTRY_NAME` varchar(255) DEFAULT NULL,
  `CONTINENT_CODE` varchar(2) DEFAULT NULL,
  `CONTINENT_NAME` varchar(255) DEFAULT NULL,
  `TIME_ZONE` varchar(20) DEFAULT NULL,
  `REGION_CODE` varchar(2) DEFAULT NULL,
  `REGION_NAME` varchar(255) DEFAULT NULL,
  `OWNER` varchar(500) DEFAULT NULL,
  `CITY_NAME` varchar(255) DEFAULT NULL,
  `COUNTY_NAME` varchar(255) DEFAULT NULL,
  `LATITUDE` varchar(20) DEFAULT NULL,
  `LONGITUDE` varchar(20) DEFAULT NULL,
  `session_id` int(20) NOT NULL,
  PRIMARY KEY (`vgd_id`),
  KEY `vgd_to_session` (`session_id`),
  KEY `vgd_to_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `user_pages`
--

CREATE TABLE IF NOT EXISTS `user_pages` (
  `vp_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `session_id` int(11) NOT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `accessed_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `department_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`vp_id`),
  KEY `vpages_to_session` (`session_id`),
  KEY `vpages_to_department` (`department_id`),
  KEY `vpages_to_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Štruktúra tabuľky pre tabuľku `user_sessions`
--

CREATE TABLE IF NOT EXISTS `user_sessions` (
  `session_id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) DEFAULT NULL,
  `session_start` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `session_end` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `browser` varchar(1000) DEFAULT NULL,
  `screen_resolution` varchar(20) DEFAULT NULL,
  `system` varchar(20) DEFAULT NULL,
  `country_code` varchar(2) DEFAULT NULL,
  `country_name` varchar(500) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`session_id`),
  KEY `user_to_session` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4361 ;

--
-- Obmedzenie pre exportované tabuľky
--

--
-- Obmedzenie pre tabuľku `accounts`
--
ALTER TABLE `accounts`
  ADD CONSTRAINT `account_to_user` FOREIGN KEY (`owner_id`) REFERENCES `users` (`user_id`);

--
-- Obmedzenie pre tabuľku `assoc_group_role`
--
ALTER TABLE `assoc_group_role`
  ADD CONSTRAINT `to_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`),
  ADD CONSTRAINT `to_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`);

--
-- Obmedzenie pre tabuľku `assoc_user_domain`
--
ALTER TABLE `assoc_user_domain`
  ADD CONSTRAINT `udassoc_to_domain` FOREIGN KEY (`domain_id`) REFERENCES `domains` (`domain_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `udassoc_to_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Obmedzenie pre tabuľku `assoc_user_groups`
--
ALTER TABLE `assoc_user_groups`
  ADD CONSTRAINT `aug_to_group` FOREIGN KEY (`group_id`) REFERENCES `groups` (`group_id`),
  ADD CONSTRAINT `aug_to_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Obmedzenie pre tabuľku `conversation`
--
ALTER TABLE `conversation`
  ADD CONSTRAINT `conversation_to_chat` FOREIGN KEY (`chat_id`) REFERENCES `users_chats` (`chat_id`);

--
-- Obmedzenie pre tabuľku `departments`
--
ALTER TABLE `departments`
  ADD CONSTRAINT `department_to_account` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  ADD CONSTRAINT `department_to_domain` FOREIGN KEY (`domain_id`) REFERENCES `domains` (`domain_id`);

--
-- Obmedzenie pre tabuľku `domains`
--
ALTER TABLE `domains`
  ADD CONSTRAINT `domain_to_account` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`);

--
-- Obmedzenie pre tabuľku `online_users`
--
ALTER TABLE `online_users`
  ADD CONSTRAINT `online_users_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `online_users_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`),
  ADD CONSTRAINT `online_user_to_session` FOREIGN KEY (`session_id`) REFERENCES `user_sessions` (`session_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Obmedzenie pre tabuľku `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `user_to_account` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Obmedzenie pre tabuľku `users_chats`
--
ALTER TABLE `users_chats`
  ADD CONSTRAINT `chat_to_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `in_chat_with_to_user` FOREIGN KEY (`in_chat_with_user_id`) REFERENCES `users` (`user_id`);

--
-- Obmedzenie pre tabuľku `user_geo_data`
--
ALTER TABLE `user_geo_data`
  ADD CONSTRAINT `vgd_to_session` FOREIGN KEY (`session_id`) REFERENCES `user_sessions` (`session_id`),
  ADD CONSTRAINT `vgd_to_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Obmedzenie pre tabuľku `user_pages`
--
ALTER TABLE `user_pages`
  ADD CONSTRAINT `vpages_to_department` FOREIGN KEY (`department_id`) REFERENCES `departments` (`department_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `vpages_to_session` FOREIGN KEY (`session_id`) REFERENCES `user_sessions` (`session_id`),
  ADD CONSTRAINT `vpages_to_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Obmedzenie pre tabuľku `user_sessions`
--
ALTER TABLE `user_sessions`
  ADD CONSTRAINT `user_to_session` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
