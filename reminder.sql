/*
SQLyog Enterprise - MySQL GUI v7.02 
MySQL - 5.0.67-community-nt : Database - reminder
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`reminder` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `reminder`;

/*Table structure for table `events` */

DROP TABLE IF EXISTS `events`;

CREATE TABLE `events` (
  `date` varchar(15) NOT NULL default '',
  `title` varchar(500) NOT NULL default '',
  PRIMARY KEY  (`date`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `events` */

insert  into `events`(`date`,`title`) values ('02/02','manoj meena b\'day'),('02/12','aditi b\'day'),('04/10','himanshu b\'day'),('07/04','lakshay bng b\'day'),('07/06','govind b\'day'),('07/09','akshay katare b\'day'),('08/04','yaashi b\'day'),('08/12','aditya vijay(tirupati) b\'day'),('08/12','annu ksgh b\'day'),('09/04','yogesh b\'day'),('10/01','pankaj b\'day'),('10/11','anupam b\'day'),('13/07','viru b\'day'),('15/08','priyanka(manoj sis) b\'day'),('16/10','ajay kumar(pandu) b\'day'),('16/12','nitin shama b\'day'),('18/01','salman b\'day'),('18/02','Tanishk b\'day'),('18/10','bhavya b\'day'),('20/06','ankit jiyaji b\'day'),('21/10','nilesh kumar b\'day'),('24/03','papa b\'day'),('24/09','shubham purohit b\'day'),('25/09','surbhi ksgh b\'day'),('25/12','lucky bhiya b\'day'),('26/12','nisha bhabhi b\'day'),('28/01','sonu bhai bhl b\'day'),('30/09','nitu & neha di b\'day'),('30/09','ritik b\'day'),('30/11','ajeet b\'day'),('30/11','utkarsh b\'day');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
