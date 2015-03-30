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
  UNIQUE (USER_ID),
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
  UNIQUE (EMAIL),
  UNIQUE (IDENTIFICATION_HASH)
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
(1, '<div class="chatBarContainerCss chatBarBcg_silver"\r\n     >\r\n    <div id="chatBarNavigation" class="chatBarNavigationCss hideButton_silver"></div>\r\n    <div id="chatBarText" class="chatBarTextCss" th:utext="#{onlineMessage}">CAN WE <br/> HELP YOU?</div>\r\n    <div id="chatNotificationIcon" class="notificationChat">1</div>\r\n\r\n</div>', NULL, 'bubble_style', 'bubble', NULL);

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

INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(31, 'en', 'welcomeText', 'Welcome to ITSENCE support', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(32, 'en', 'offlineMessage', 'LEAVE US<br />A MESSAGE?', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(33, 'en', 'leftOfflineMessage', 'We are offline now. <br/>Please left us a message we will responce to you.', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(34, 'en', 'onlineMessage', 'CAN WE<br />HELP YOU?', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(35, 'en', 'requesting', '<div class="loader_#{template} loader"></div><div style="font-size:11px">SENDING REQUEST</div>', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(36, 'en', 'afterSentOfflineMessage', 'YOUR MESSAGE<br />HAS BEAN SENT', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(37, 'en', 'inChatWithMessage', 'IN CHAT WITH<br/>#{name}', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(38, 'en', 'newMessageNotification', 'YOU HAVE<br />NEW MESSAGE', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(39, 'en', 'registrationText', 'Hello! It''s really great to see you here.<br/> Tell us just a few details about you and we are ready<br/> to go!', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(40, 'sk', 'welcomeText', 'V�tajte na na�ej podpore', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(41, 'sk', 'offlineMessage', 'NECHAJTE N�M<br /> SPR�VU', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(42, 'sk', 'leftOfflineMessage', 'Momnt�lne tu nie sme. <br /> Pros�m nechajte n�m spr�vu a my V�m odpovieme v co najkrat�om case.', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(43, 'sk', 'onlineMessage', 'MO�EME V�M<br /> POMOCT?', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(44, 'sk', 'requesting', '<div class="loader_#{template} loader"></div><div style="font-size:11px">POSIELAM PO�IADAVKU</div>', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(45, 'sk', 'afterSentOfflineMessage', 'VA�A SPR�VA<br /> BOLA ODOSLAN�', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(46, 'sk', 'inChatWithMessage', 'CETUJETE S<br />#{name}', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(47, 'sk', 'newMessageNotification', 'M�TE NOV� SPR�VU', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(48, 'sk', 'registrationText', 'Dobr� den. <br /> Povedzte n�m nieco o sebe a m��eme zacat.', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(49, 'en', 'registrationYourName', 'Your Name', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(50, 'en', 'registrationEmail', 'Your Email', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(51, 'en', 'registrationInfo', 'Please press "START CHAT" button to connect with our<br /> live representatives.', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(52, 'en', 'userRegistrationButton', 'START CHAT', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(53, 'sk', 'userRegistrationButton', 'CETUJ', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(54, 'en', 'userInfoRememberInputLabel', 'Remember Me', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(55, 'sk', 'userInfoRememberInputLabel', 'Zapametat si', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(56, 'en', 'chatMessageNotification', 'Press enter to send message', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(57, 'sk', 'chatMessageNotification', 'Pre odoslanie stlacte kl�vesu enter', 'SK');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(58, 'en', 'chatInitationOnlineMessage', 'Now online: Leave a question or comment and our agents will try to attend to you shortly', 'GB');
INSERT INTO MESSAGE_RESOURCE (id, lang_iso_code, text_code, text_value, coutry_iso_code) VALUES
(59, 'sk', 'chatInitationOnlineMessage', 'Sme online: Nap�te n�m spr�vu a my V�m odpoviem v kr�tkom case. ', 'SK');

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


