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
-- Datenbank: `ud`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserDatabase`
--

CREATE TABLE `userdatabase` (
  `username` varchar(500) NOT NULL,
  `email` varchar(500) NOT NULL UNIQUE,
  `age` int(11) NOT NULL CHECK ((`age` > 17)),
  primary key (`username`)
   
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

COMMIT;