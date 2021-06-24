--[[[ INITCODE5DBBYDEV_01

DROP TABLE BZ_BOARD;

--]]]
--[[[ INITCODE5DBBYDEV_02

CREATE TABLE BZ_BOARD(
N INTEGER PRIMARY KEY AUTOINCREMENT
, TITLE
, TXT
, EM
, FILE_NM_1
, FILE_ID_1
, FILE_NM_2
, FILE_ID_2
, RG_ID
, RG_IP
, RG_DTM
, MDF_ID
, MDF_IP
, MDF_DTM
, DEL_Y
);

--]]]


--[[[ INITCODE5DBBYDEV_03

DROP TABLE BZ_ID;

--]]]
--[[[ INITCODE5DBBYDEV_04

CREATE TABLE BZ_ID (
ID PRIMARY KEY
, PIN
, AUTH
, FAIL_CNT
, LAST_LOGIN_DTM
);

--]]]
--[[[ INITCODE5DBBYDEV_05


INSERT INTO BZ_ID VALUES(
'admin'
,'1'
,'A0'
,'0'
,''
);

--]]]
--[[[ INITCODE5DBBYDEV_06

INSERT INTO BZ_ID VALUES(
'user1'
,'1'
,'U0'
,'0'
,''
);

--]]]
--[[[ INITCODE5DBBYDEV_07

INSERT INTO BZ_ID VALUES(
'user2'
,'1'
,'U0'
,'0'
,''
);

--]]]
--[[[ INITCODE5DBBYDEV_08


drop table FW_CONTROLLER;

--]]]
--[[[ INITCODE5DBBYDEV_09

CREATE TABLE FW_CONTROLLER(
KEY
, CLASS_NAME
, METHOD_NAME
);

--]]]

--[[[ INITCODE5DBBYDEV_10


drop TABLE FW_VIEW ;

--]]]
--[[[ INITCODE5DBBYDEV_11

CREATE TABLE FW_VIEW (
KEY PRIMARY KEY
, VIEW
);

--]]]



--[[[ INITCODE5DBBYDEV_12
INSERT INTO FW_CONTROLLER VALUES('callList','com.biz.board.Board','callList');
--]]]

--[[[ INITCODE5DBBYDEV_13
INSERT INTO FW_CONTROLLER VALUES('callWrite','com.biz.board.Board','callWrite');
--]]]

--[[[ INITCODE5DBBYDEV_14
INSERT INTO FW_CONTROLLER VALUES('exeWrite','com.biz.board.Board','exeWrite');
--]]]

--[[[ INITCODE5DBBYDEV_15
INSERT INTO FW_VIEW VALUES('list','/WEB-INF/classes/com/biz/board/jsp/list.jsp');
--]]]
--[[[ INITCODE5DBBYDEV_16
INSERT INTO FW_VIEW VALUES('write','/WEB-INF/classes/com/biz/board/jsp/write.jsp');
--]]]



--[[[ INITCODE5DBBYDEV_17
INSERT INTO FW_CONTROLLER VALUES('exeLogin','com.biz.login.Login','exeLogin');
--]]]

--[[[ INITCODE5DBBYDEV_18
INSERT INTO FW_CONTROLLER VALUES('callLogin','com.biz.login.Login','callLogin');
--]]]

--[[[ INITCODE5DBBYDEV_19
INSERT INTO FW_VIEW VALUES('login','/WEB-INF/classes/com/biz/login/jsp/login.jsp');
--]]]
