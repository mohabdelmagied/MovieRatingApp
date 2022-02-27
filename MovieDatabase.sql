-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 13. Jan 2022 um 18:30
-- Server-Version: 10.1.37-MariaDB
-- PHP-Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `md`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MovieDatabase`
--

CREATE TABLE `moviedatabase` (
  `movie_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `publishingDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `director` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `mainActors` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `avg_rating` double(16,2) NOT NULL default 0.0,
  primary key (`movie_id`),
  CONSTRAINT `CHK_rating` CHECK ((`avg_rating` > -1 AND `avg_rating` < 11))
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `rating`
--

CREATE TABLE `rating` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(500) not null,
  `postingdate` timestamp NULL DEFAULT NULL,
  `comment` longtext,
  `rate` int(11) NOT NULL,
  `m_id` double NOT NULL REFERENCES `moviedatabase` (`movie_id`),
  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
