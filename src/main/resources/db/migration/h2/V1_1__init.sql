CREATE SCHEMA IF NOT EXISTS mylivedata;
SET SCHEMA mylivedata;
SET IGNORECASE TRUE;

CREATE TABLE IF NOT EXISTS ACCOUNTS (
  ACCOUNT_ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  OWNER_ID int(11) DEFAULT NULL,
  BILLING_EMAIL varchar(255) DEFAULT NULL,
  COMPANY_NAME varchar(500) DEFAULT NULL,
  STREET_ADRRESS_LINE_1 varchar(1000) DEFAULT NULL,
  STREET_ADDRESS_LINE_2 varchar(1000) DEFAULT NULL,
  CITY varchar(500) DEFAULT NULL,
  STATE_PROVINCE varchar(500) DEFAULT NULL,
  ZIP varchar(20) DEFAULT NULL,
  COUNTRY varchar(500) DEFAULT NULL,
  PHONE varchar(50) DEFAULT NULL,
  REGISTRATION_CODE varchar(255) DEFAULT NULL,
  IS_VERIEFIED tinyint(1) NOT NULL DEFAULT '0',
  ACCOUNT_IDENTITY varchar(255) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS ASSOC_GROUP_ROLE (
  ROLE_ID int(11) NOT NULL,
  GROUP_ID int(11) NOT NULL,
  PRIMARY KEY (ROLE_ID, GROUP_ID)
);

CREATE TABLE IF NOT EXISTS ASSOC_USER_DOMAIN (
  USER_ID int(11) NOT NULL,
  DOMAIN_ID int(11) NOT NULL,
  PRIMARY KEY (USER_ID, DOMAIN_ID)
);

CREATE TABLE IF NOT EXISTS ASSOC_USER_GROUPS (
  USER_ID int(11) NOT NULL,
  GROUP_ID int(11) NOT NULL,
  PRIMARY KEY (USER_ID, GROUP_ID)
)  ;

CREATE TABLE IF NOT EXISTS CONVERSATION (
  CONVERSATION_ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CHAT_ID int(11) NOT NULL,
  MESSAGE_BY int(11) NULL,
  MESSAGE_TEXT varchar 
) ;

CREATE TABLE IF NOT EXISTS DEFAULT_LAYOUTS (
  LAYOUT_ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  HTML varchar ,
  CSS varchar ,
  NAME varchar(50) DEFAULT NULL,
  FRAGMENT varchar(50) DEFAULT NULL,
  DESCRIPTION varchar(500) DEFAULT NULL
) ;

CREATE TABLE IF NOT EXISTS DEPARTMENTS (
  DEPARTMENT_ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ACCOUNT_ID int(11) NOT NULL,
  DOMAIN_ID int(11) DEFAULT NULL,
  KEY_NAME varchar(20) DEFAULT NULL,
  TITLE varchar(500) DEFAULT NULL
) ;

CREATE TABLE IF NOT EXISTS DOMAINS (
  DOMAIN_ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  DOMAIN varchar(255) NOT NULL,
  DESCRIPTION varchar(1000) DEFAULT NULL,
  ACCOUNT_ID int(11) NOT NULL,
  VISIBLE_OFFLINE varchar DEFAULT 'Yes'
) ;

CREATE TABLE IF NOT EXISTS GROUPS (
  GROUP_ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  GROUP_NAME varchar(100) NOT NULL,
  DESCRIPTION varchar(1000) DEFAULT NULL
) ;

CREATE TABLE IF NOT EXISTS MESSAGE_RESOURCE (
  ID int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  LANG_ISO_CODE varchar(2) DEFAULT NULL,
  TEXT_CODE varchar(100) DEFAULT NULL,
  TEXT_VALUE varchar(1000) DEFAULT NULL,
  COUTRY_ISO_CODE varchar(2) DEFAULT NULL
) ;

CREATE TABLE IF NOT EXISTS ONLINE_USERS (
  USER_ID int(11) NOT NULL DEFAULT '0' PRIMARY KEY,
  STATUS int(11) NOT NULL DEFAULT '0',
  IS_ONLINE int(11) NOT NULL DEFAULT '0',
  DEPARTMENT_ID int(11) DEFAULT NULL,
  IS_CHAT_HISTORY int(11) NOT NULL DEFAULT '0',
  IS_IN_CHAT int(11) NOT NULL DEFAULT '0',
  IS_OPERATOR int(11) NOT NULL DEFAULT '0',
  SESSION_ID int(11) DEFAULT NULL,
  IS_HTTP_ONLINE int(11) NOT NULL DEFAULT '0',
  UNIQUE KEY unique_user_id (USER_ID),
)  ;

CREATE TABLE IF NOT EXISTS PERSISTENT_LOGINS (
  USERNAME varchar(64) NOT NULL,
  SERIES varchar(64) NOT NULL PRIMARY KEY,
  TOKEN varchar(64) NOT NULL,
  LAST_USED timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP 
)  ;

CREATE TABLE IF NOT EXISTS ROLES (
  ROLE_ID int(11) NOT NULL AUTO_INCREMENT,
  ROLE_NAME varchar(100) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  PRIMARY KEY (ROLE_ID)
);

CREATE TABLE IF NOT EXISTS USERCONNECTION (
  USERID int(11) NOT NULL,
  PROVIDERID varchar(100) NOT NULL,
  PROVIDERUSERID varchar(50) NOT NULL,
  DISPLAYNAME varchar(255) DEFAULT NULL,
  PROFILEURL varchar(255) DEFAULT NULL,
  IMAGEURL varchar(255) DEFAULT NULL,
  ACCESSTOKEN varchar(200) DEFAULT NULL,
  SECRET varchar(150) DEFAULT NULL,
  REFRESHTOKEN varchar(150) DEFAULT NULL,
  EXPIRETIME varchar(200) DEFAULT NULL,
  PRIMARY KEY (USERID)
)  ;

CREATE TABLE IF NOT EXISTS USERS (
  USER_ID int(11) NOT NULL AUTO_INCREMENT,
  NAME varchar(500) DEFAULT NULL,
  SURRNAME varchar(500) DEFAULT NULL,
  EMAIL varchar(255) DEFAULT NULL,
  PASSWORD varchar(1000) DEFAULT NULL,
  REGISTRATION timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  LAST_LOGIN timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  LAST_IP varchar(50) DEFAULT NULL,
  IDENTIFICATION_HASH varchar(255) DEFAULT NULL,
  ACCOUNT_ID int(11) NOT NULL,
  IS_CREDENTIALS_NON_EXPIRED varchar NOT NULL DEFAULT 'true',
  IMAGE_URL varchar(500) DEFAULT NULL,
  PRIMARY KEY (USER_ID),
  UNIQUE KEY email (EMAIL),
  UNIQUE KEY unique_identification_hash (IDENTIFICATION_HASH)
) ;

CREATE TABLE IF NOT EXISTS USERS_CHATS (
  CHAT_ID int(11) NOT NULL AUTO_INCREMENT,
  USER_ID int(11) NOT NULL,
  IN_CHAT_WITH_USER_ID int(11) NOT NULL,
  INITIALIZED_BY varchar NOT NULL,
  START timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  END timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  SATISFACTION int(11) DEFAULT NULL,
  PRIMARY KEY (CHAT_ID)
) ;

CREATE TABLE IF NOT EXISTS USER_GEO_DATA (
  VGD_ID int(11) NOT NULL AUTO_INCREMENT,
  USER_ID int(11) NOT NULL,
  COUNTRY_CODE varchar(2) DEFAULT NULL,
  COUNTRY_NAME varchar(255) DEFAULT NULL,
  CONTINENT_CODE varchar(2) DEFAULT NULL,
  CONTINENT_NAME varchar(255) DEFAULT NULL,
  TIME_ZONE varchar(20) DEFAULT NULL,
  REGION_CODE varchar(2) DEFAULT NULL,
  REGION_NAME varchar(255) DEFAULT NULL,
  OWNER varchar(500) DEFAULT NULL,
  CITY_NAME varchar(255) DEFAULT NULL,
  COUNTY_NAME varchar(255) DEFAULT NULL,
  LATITUDE varchar(20) DEFAULT NULL,
  LONGITUDE varchar(20) DEFAULT NULL,
  SESSION_ID int(20) NOT NULL,
  PRIMARY KEY (VGD_ID)
);

CREATE TABLE IF NOT EXISTS USER_PAGES (
  VP_ID int(11) NOT NULL AUTO_INCREMENT,
  USER_ID int(11) NOT NULL,
  SESSION_ID int(11) NOT NULL,
  URL varchar(1000) DEFAULT NULL,
  ACCESSED_ON timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  DEPARTMENT_ID int(11) DEFAULT NULL,
  PRIMARY KEY (VP_ID)
) ;

CREATE TABLE IF NOT EXISTS USER_SESSIONS (
  SESSION_ID int(11) NOT NULL AUTO_INCREMENT,
  IP varchar(50) DEFAULT NULL,
  SESSION_START timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  SESSION_END timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  BROWSER varchar(1000) DEFAULT NULL,
  SCREEN_RESOLUTION varchar(20) DEFAULT NULL,
  SYSTEM varchar(20) DEFAULT NULL,
  COUNTRY_CODE varchar(2) DEFAULT NULL,
  COUNTRY_NAME varchar(500) DEFAULT NULL,
  USER_ID int(11) DEFAULT NULL,
  PRIMARY KEY (SESSION_ID)
) ;


CREATE TABLE IF NOT EXISTS ip2location_db11 (
  ip_from BIGINT NOT NULL DEFAULT '0',
  ip_to BIGINT NOT NULL DEFAULT '0',
  country_code char(2) DEFAULT NULL,
  country_name varchar(64) DEFAULT NULL,
  region_name varchar(128) DEFAULT NULL,
  city_name varchar(128) DEFAULT NULL,
  latitude double DEFAULT NULL,
  longitude double DEFAULT NULL,
  zip_code varchar(30) DEFAULT NULL,
  time_zone varchar(8) DEFAULT NULL,
  PRIMARY KEY (ip_from,ip_to)
) ;

INSERT INTO ROLES (role_id, role_name, description) VALUES
(1, 'ROLE_USER', 'has rights to use dashboard');
INSERT INTO ROLES (role_id, role_name, description) VALUES
(2, 'ROLE_ADMIN', 'has rights to administrate');
INSERT INTO ROLES (role_id, role_name, description) VALUES
(3, 'ROLE_VISITOR', 'Your page site visitors');

INSERT INTO GROUPS (group_id, group_name, description) VALUES
(1, 'admin', 'chat support administrator');
INSERT INTO GROUPS (group_id, group_name, description) VALUES
(2, 'operator', 'chat operator');
INSERT INTO GROUPS (group_id, group_name, description) VALUES
(3, 'visitor', 'Your Page Visitor');

INSERT INTO ACCOUNTS (account_id, owner_id, billing_email, company_name, street_adrress_line_1, street_address_line_2, City, state_province, zip, country, phone, registration_code, is_veriefied, account_identity) VALUES
(1, 1, 'lubo08@hotmail.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 'd3e5fa6c-6840-4721-aee0-6c391fde4472');

INSERT INTO USERS (user_id, name, surrname, email, password, registration, last_login, last_ip, identification_hash, account_id, is_credentials_non_expired, image_url) VALUES
(1, 'lubo08', 'Developer', 'lubo08@hotmail.com', '$2a$10$8A.CHyB46V9Lli7911ROC.rsnhQN1XLl5ioyKdsV0cPkWV69zXAYu', '2014-12-15 13:08:46', '2014-04-15 23:14:44', NULL, NULL, 1, 'true', NULL);

INSERT INTO DEFAULT_LAYOUTS (layout_id, html, css, name, fragment, description) VALUES
(1, '<div class="chatBarContainerCss chatBarBcg_silver"><div id="chatBarNavigation" class="chatBarNavigationCss hideButton_silver"></div><div id="chatBarText" class="chatBarTextCss" th:utext="#{offlineMessage}">CAN WE <br/> HELP YOU?</div><div id="chatNotificationIcon" class="notificationChat">1</div></div>', NULL, 'silver_chat', 'chat_bar_offline', NULL);
INSERT INTO DEFAULT_LAYOUTS (layout_id, html, css, name, fragment, description) VALUES
  (2, '<div class="chatBarContainerCss chatBarBcg_silver"><div id="chatBarNavigation" class="chatBarNavigationCss hideButton_silver"></div><div id="chatBarText" class="chatBarTextCss" th:utext="#{onlineMessage}">CAN WE <br/> HELP YOU?</div></div>', NULL, 'silver_chat', 'chat_bar_online', NULL);

INSERT INTO DOMAINS (domain_id, domain, description, account_id, visible_offline) VALUES
(1, 'testweb.com', '', 1, 'Yes');

INSERT INTO ASSOC_USER_GROUPS VALUES (1,1);

INSERT INTO ASSOC_GROUP_ROLE (ROLE_ID, GROUP_ID) VALUES
(1, 1);
INSERT INTO ASSOC_GROUP_ROLE (ROLE_ID, GROUP_ID) VALUES
(2, 1);
INSERT INTO ASSOC_GROUP_ROLE (ROLE_ID, GROUP_ID) VALUES
(1, 2);
INSERT INTO ASSOC_GROUP_ROLE (ROLE_ID, GROUP_ID) VALUES
(3, 3);

INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(31, 'en', 'welcomeText', 'Welcome to ITSENCE support', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(32, 'en', 'offlineMessage', 'LEAVE US<br />A MESSAGE?', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(33, 'en', 'leftOfflineMessage', 'We are offline now. <br/>Please left us a message we will responce to you.', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(34, 'en', 'onlineMessage', 'CAN WE<br />HELP YOU?', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(35, 'en', 'requesting', '<div class="loader_#{template} loader"></div><div style="font-size:11px">SENDING REQUEST</div>', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(36, 'en', 'afterSentOfflineMessage', 'YOUR MESSAGE<br />HAS BEAN SENT', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(37, 'en', 'inChatWithMessage', 'IN CHAT WITH<br/>#{name}', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(38, 'en', 'newMessageNotification', 'YOU HAVE<br />NEW MESSAGE', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(39, 'en', 'registrationText', 'Hello! It''s really great to see you here.<br/> Tell us just a few details about you and we are ready<br/> to go!', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(40, 'sk', 'welcomeText', 'Vítajte na našej podpore', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(41, 'sk', 'offlineMessage', 'ZANECHAŤ<br /> SPRÁVU', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(42, 'sk', 'leftOfflineMessage', 'Momntálne tu nie sme. <br /> Prosím nechajte nám správu a my Vám odpovieme v co najkratšom case.', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(43, 'sk', 'onlineMessage', 'MOŽEME VÁM<br /> POMOCT?', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(44, 'sk', 'requesting', '<div class="loader_#{template} loader"></div><div style="font-size:11px">POSIELAM POŽIADAVKU</div>', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(45, 'sk', 'afterSentOfflineMessage', 'VAŠA SPRÁVA<br /> BOLA ODOSLANÁ', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(46, 'sk', 'inChatWithMessage', 'CETUJETE S<br />#{name}', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(47, 'sk', 'newMessageNotification', 'MÁTE NOVÚ SPRÁVU', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(48, 'sk', 'registrationText', 'Dobrý den. <br /> Povedzte nám nieco o sebe a môžeme zacat.', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(49, 'en', 'registrationYourName', 'Your Name', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(50, 'en', 'registrationEmail', 'Your Email', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(51, 'en', 'registrationInfo', 'Please press "START CHAT" button to connect with our<br /> live representatives.', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(52, 'en', 'userRegistrationButton', 'START CHAT', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(53, 'sk', 'userRegistrationButton', 'ČETUJ', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(54, 'en', 'userInfoRememberInputLabel', 'Remember Me', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(55, 'sk', 'userInfoRememberInputLabel', 'Zapametat si', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(56, 'en', 'chatMessageNotification', 'Press enter to send message', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(57, 'sk', 'chatMessageNotification', 'Pre odoslanie stlacte klávesu enter', 'SK');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(58, 'en', 'chatInitationOnlineMessage', 'Now online: Leave a question or comment and our agents will try to attend to you shortly', 'GB');
INSERT INTO  message_resource  ( id ,  lang_iso_code ,  text_code ,  text_value ,  coutry_iso_code ) VALUES(59, 'sk', 'chatInitationOnlineMessage', 'Sme online: Napíšte nám správu a my Vám odpoviem v krátkom case. ', 'SK');


ALTER TABLE ACCOUNTS
  ADD CONSTRAINT ACCOUNT_TO_USER FOREIGN KEY (OWNER_ID) REFERENCES USERS (USER_ID);

ALTER TABLE ASSOC_GROUP_ROLE
  ADD CONSTRAINT TO_GROUP FOREIGN KEY (GROUP_ID) REFERENCES GROUPS (GROUP_ID);
ALTER TABLE ASSOC_GROUP_ROLE
  ADD CONSTRAINT TO_ROLE FOREIGN KEY (ROLE_ID) REFERENCES ROLES (ROLE_ID);

ALTER TABLE ASSOC_USER_DOMAIN
  ADD CONSTRAINT UDASSOC_TO_DOMAIN FOREIGN KEY (DOMAIN_ID) REFERENCES DOMAINS (DOMAIN_ID);
ALTER TABLE ASSOC_USER_DOMAIN
  ADD CONSTRAINT UDASSOC_TO_USER FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);

ALTER TABLE ASSOC_USER_GROUPS
  ADD CONSTRAINT AUG_TO_GROUP FOREIGN KEY (GROUP_ID) REFERENCES GROUPS (GROUP_ID);
ALTER TABLE ASSOC_USER_GROUPS
  ADD CONSTRAINT AUG_TO_USER FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);

ALTER TABLE CONVERSATION
  ADD CONSTRAINT CONVERSATION_TO_CHAT FOREIGN KEY (CHAT_ID) REFERENCES USERS_CHATS (CHAT_ID);

ALTER TABLE DEPARTMENTS
  ADD CONSTRAINT DEPARTMENT_TO_ACCOUNT FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNTS (ACCOUNT_ID);
ALTER TABLE DEPARTMENTS
  ADD CONSTRAINT DEPARTMENT_TO_DOMAIN FOREIGN KEY (DOMAIN_ID) REFERENCES DOMAINS (DOMAIN_ID);

ALTER TABLE DOMAINS
  ADD CONSTRAINT DOMAIN_TO_ACCOUNT FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNTS (ACCOUNT_ID);

ALTER TABLE ONLINE_USERS
  ADD CONSTRAINT ONLINE_USERS_IBFK_1 FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);
ALTER TABLE ONLINE_USERS
  ADD CONSTRAINT ONLINE_USERS_IBFK_2 FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENTS (DEPARTMENT_ID);
ALTER TABLE ONLINE_USERS
  ADD CONSTRAINT ONLINE_USER_TO_SESSION FOREIGN KEY (SESSION_ID) REFERENCES USER_SESSIONS (SESSION_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE USERS
  ADD CONSTRAINT USER_TO_ACCOUNT FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNTS (ACCOUNT_ID) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE USERS_CHATS
  ADD CONSTRAINT CHAT_TO_USER FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);
ALTER TABLE USERS_CHATS
  ADD CONSTRAINT IN_CHAT_WITH_TO_USER FOREIGN KEY (IN_CHAT_WITH_USER_ID) REFERENCES USERS (USER_ID);

ALTER TABLE USER_GEO_DATA
  ADD CONSTRAINT VGD_TO_SESSION FOREIGN KEY (SESSION_ID) REFERENCES USER_SESSIONS (SESSION_ID);
ALTER TABLE USER_GEO_DATA
  ADD CONSTRAINT VGD_TO_USER FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);

ALTER TABLE USER_PAGES
  ADD CONSTRAINT VPAGES_TO_DEPARTMENT FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENTS (DEPARTMENT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE USER_PAGES
  ADD CONSTRAINT VPAGES_TO_SESSION FOREIGN KEY (SESSION_ID) REFERENCES USER_SESSIONS (SESSION_ID);
ALTER TABLE USER_PAGES
  ADD CONSTRAINT VPAGES_TO_USER FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID);

ALTER TABLE USER_SESSIONS
  ADD CONSTRAINT USER_TO_SESSION FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID) ON DELETE NO ACTION ON UPDATE CASCADE;


