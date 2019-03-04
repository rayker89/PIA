-- phpMyAdmin SQL Dump
-- version 3.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 19, 2017 at 08:00 PM
-- Server version: 5.5.25a
-- PHP Version: 5.4.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `pia`
--

-- --------------------------------------------------------

--
-- Table structure for table `festival`
--

CREATE TABLE IF NOT EXISTS `festival` (
  `naziv` varchar(30) NOT NULL,
  `vremeOd` date NOT NULL,
  `vremeDo` date NOT NULL,
  `mesto` varchar(15) NOT NULL,
  `brUlaznica` int(6) NOT NULL,
  `cenaZaDan` int(5) NOT NULL,
  `canaZaPaket` int(5) NOT NULL,
  `ocena` int(11) NOT NULL,
  `maxKarata` int(2) NOT NULL,
  `maxDnevno` int(6) NOT NULL,
  `poDanima` text NOT NULL,
  PRIMARY KEY (`naziv`),
  UNIQUE KEY `naziv` (`naziv`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `festival`
--

INSERT INTO `festival` (`naziv`, `vremeOd`, `vremeDo`, `mesto`, `brUlaznica`, `cenaZaDan`, `canaZaPaket`, `ocena`, `maxKarata`, `maxDnevno`, `poDanima`) VALUES
('Beer Fest', '2017-08-09', '2017-08-13', 'Beograd', 749985, 500, 2000, 5, 10, 150000, '{Wed Aug 09 00:00:00 CEST 2017=149980, Sat Aug 12 00:00:00 CEST 2017=150000, Fri Aug 11 00:00:00 CEST 2017=150000, Sun Aug 13 00:00:00 CEST 2017=150000, Thu Aug 10 00:00:00 CEST 2017=150005}'),
('Exit', '2017-07-05', '2017-07-09', 'Novi Sad', 300005, 2000, 6000, 10, 3, 60000, '{Wed Jul 05 00:00:00 CEST 2017=60001, Fri Jul 07 00:00:00 CEST 2017=60001, Thu Jul 06 00:00:00 CEST 2017=60001, Sun Jul 09 00:00:00 CEST 2017=60001, Sat Jul 08 00:00:00 CEST 2017=60001}'),
('Fusion Festival', '2017-06-16', '2017-06-18', 'Larz', 300000, 1000, 2000, 4, 5, 100000, '{Sat Jun 17 00:00:00 CEST 2017=100000, Fri Jun 16 00:00:00 CEST 2017=100000, Sun Jun 18 00:00:00 CEST 2017=100000}'),
('Gitarijada', '2017-06-22', '2017-06-25', 'Zajecar', 20005, 200, 500, 6, 10, 5000, '{Sun Jun 25 00:00:00 CEST 2017=5000, Sat Jun 24 00:00:00 CEST 2017=5000, Thu Jun 22 00:00:00 CEST 2017=5000, Fri Jun 23 00:00:00 CEST 2017=5005}'),
('Guca', '2017-08-01', '2017-08-06', 'Guca', 599988, 300, 1500, 8, 15, 100000, '{Fri Aug 04 00:00:00 CEST 2017=99998, Tue Aug 01 00:00:00 CEST 2017=99998, Sun Aug 06 00:00:00 CEST 2017=99998, Thu Aug 03 00:00:00 CEST 2017=99998, Wed Aug 02 00:00:00 CEST 2017=99998, Sat Aug 05 00:00:00 CEST 2017=99998}'),
('Jazz Fest', '2017-06-15', '2017-06-18', 'Nis', 60000, 1000, 3000, 6, 10, 15000, '{Thu Jun 15 00:00:00 CEST 2017=15000, Sat Jun 17 00:00:00 CEST 2017=15000, Fri Jun 16 00:00:00 CEST 2017=15000, Sun Jun 18 00:00:00 CEST 2017=15000}'),
('Love Fest', '2017-02-19', '2017-02-22', 'Banja', 13, 200, 300, 4, 5, 1000, '{Mon Feb 20 00:00:00 CET 2017=1, Tue Feb 21 00:00:00 CET 2017=996, Wed Feb 22 00:00:00 CET 2017=999, Sun Feb 19 00:00:00 CET 2017=999}'),
('Oktobarfest', '2017-09-16', '2017-09-30', 'Minhen', 900000, 1000, 10000, 7, 10, 45000, '{Mon Sep 18 00:00:00 CEST 2017=45000, Sat Sep 23 00:00:00 CEST 2017=45000, Thu Sep 28 00:00:00 CEST 2017=45000, Sun Sep 17 00:00:00 CEST 2017=45000, Fri Sep 22 00:00:00 CEST 2017=45000, Wed Sep 27 00:00:00 CEST 2017=45000, Sat Sep 16 00:00:00 CEST 2017=45000, Thu Sep 21 00:00:00 CEST 2017=45000, Tue Sep 26 00:00:00 CEST 2017=45000, Wed Sep 20 00:00:00 CEST 2017=45000, Mon Sep 25 00:00:00 CEST 2017=45000, Sat Sep 30 00:00:00 CEST 2017=45000, Fri Sep 29 00:00:00 CEST 2017=45000, Tue Sep 19 00:00:00 CEST 2017=45000, Sun Sep 24 00:00:00 CEST 2017=45000}');

-- --------------------------------------------------------

--
-- Table structure for table `galerija`
--

CREATE TABLE IF NOT EXISTS `galerija` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `festival` varchar(30) NOT NULL,
  `naziv` varchar(30) NOT NULL,
  `vrsta` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `festival` (`festival`),
  KEY `festival_2` (`festival`),
  KEY `festival_3` (`festival`),
  KEY `festival_4` (`festival`),
  KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `galerija`
--

INSERT INTO `galerija` (`id`, `festival`, `naziv`, `vrsta`) VALUES
(1, 'Beer Fest', 'beerfest1', 0),
(2, 'Beer Fest', 'beerfest2', 0),
(3, 'Beer Fest', 'beerfest3', 0);

-- --------------------------------------------------------

--
-- Table structure for table `izvodjaci`
--

CREATE TABLE IF NOT EXISTS `izvodjaci` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `festival` varchar(30) NOT NULL,
  `naziv` varchar(30) NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `naziv` (`naziv`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `izvodjaci`
--

INSERT INTO `izvodjaci` (`id`, `festival`, `naziv`, `start`, `end`) VALUES
(1, 'Beer Fest', 'Riblja Corba', '2017-08-09 20:00:00', '2017-08-09 21:30:00'),
(2, 'Beer Fest', 'Kerber', '2017-08-10 22:00:00', '2017-08-10 23:30:00'),
(3, 'Beer Fest', 'Viva Vox', '2017-08-09 22:10:00', '2017-08-09 23:30:00'),
(4, 'Beer Fest', 'Legende', '2017-08-10 20:00:00', '2017-08-10 21:30:00'),
(5, 'guca', 'Kerber', '2017-08-04 20:00:00', '2017-08-04 22:00:00'),
(10, 'Love Fest', 'Ceca', '2017-03-01 20:00:00', '2017-03-01 22:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `komentari`
--

CREATE TABLE IF NOT EXISTS `komentari` (
  `username` varchar(30) NOT NULL,
  `festival` varchar(30) NOT NULL,
  `komentar` text NOT NULL,
  PRIMARY KEY (`festival`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `komentari`
--

INSERT INTO `komentari` (`username`, `festival`, `komentar`) VALUES
('sofija', 'Beer Fest', 'Sve najbolje, iskrena preporuka!!!');

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE IF NOT EXISTS `korisnik` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(15) NOT NULL,
  `prezime` varchar(15) NOT NULL,
  `korisnickoIme` varchar(20) NOT NULL,
  `lozinka` varchar(13) NOT NULL,
  `telefon` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  `tip` int(1) NOT NULL,
  `aktivan` int(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `korisnickoIme` (`korisnickoIme`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`ID`, `ime`, `prezime`, `korisnickoIme`, `lozinka`, `telefon`, `email`, `tip`, `aktivan`) VALUES
(2, 'Nenad', 'Rajic', 'rayker', 'NenaD1411', '062635155', 'rajkerpn@yahoo.com', 1, 0),
(4, 'Milan', 'Nesic', 'milance', 'MilAn90', '060112233', 'rajkerpn@yahoo.com', 1, 0),
(5, 'Sofija', 'Rajic', 'sofija', 'Sofija96', '060112233', 'rajkerpn@yahoo.com', 0, 0),
(8, 'Riki', 'Tan', 'rikson', 'RiKsOn96', '061234567', 'rajkerpn@yahoo.com', 0, 0),
(11, 'Nikola', 'Nesic', 'nikolce', 'Nikola87', '061234567', 'rajkerpn@yahoo.com', 0, 0),
(12, 'Aleksandar', 'Gligorijevic', 'aca89', 'Aleksandar89', '063665544', 'rajkerpn@yahoo.com', 0, 0),
(13, 'Nenad', 'Brankovic', 'rayker889', 'Nenad89', '060121212', 'rajkerpn@yahoo.com', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `registracije`
--

CREATE TABLE IF NOT EXISTS `registracije` (
  `ime` varchar(15) NOT NULL,
  `prezime` varchar(15) NOT NULL,
  `korisnickoIme` varchar(20) NOT NULL,
  `lozinka` varchar(13) NOT NULL,
  `telefon` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  PRIMARY KEY (`korisnickoIme`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `registracije`
--

INSERT INTO `registracije` (`ime`, `prezime`, `korisnickoIme`, `lozinka`, `telefon`, `email`) VALUES
('Danilo', 'Brankovic', 'Danilo', 'Danilo123', '060121212', 'danilo@gmail.com'),
('Marina', 'Svrzic', 'maki92', 'Marina123', '064998877', 'maki@gmail.com'),
('Marko', 'Svrzic', 'Marko', 'Marko123', '060334455', 'marko@gmail.com'),
('Nenad', 'Rajic', 'rayker123', 'Nenad123', '060121212', 'rajkerpn@yahoo.com');

-- --------------------------------------------------------

--
-- Table structure for table `rezervacije`
--

CREATE TABLE IF NOT EXISTS `rezervacije` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `festival` varchar(30) NOT NULL,
  `brojUlaznica` int(2) NOT NULL,
  `korisnickoIme` varchar(15) NOT NULL,
  `datum` date NOT NULL,
  `paket` int(1) NOT NULL,
  `kupljeno` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `rezervacije`
--

INSERT INTO `rezervacije` (`id`, `festival`, `brojUlaznica`, `korisnickoIme`, `datum`, `paket`, `kupljeno`) VALUES
(8, 'Beer Fest', 5, 'sofija', '2017-08-09', 0, 0),
(10, 'Guca', 3, 'sofija', '2017-02-19', 1, 0),
(11, 'Guca', 2, 'sofija', '2017-02-19', 1, 0);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `galerija`
--
ALTER TABLE `galerija`
  ADD CONSTRAINT `galerija_ibfk_1` FOREIGN KEY (`festival`) REFERENCES `festival` (`naziv`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
