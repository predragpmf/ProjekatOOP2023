-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 21, 2023 at 08:53 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pozoriste`
--

-- --------------------------------------------------------

--
-- Table structure for table `izvodjenje_predstave`
--

CREATE TABLE `izvodjenje_predstave` (
  `id` int(11) NOT NULL,
  `predstava_id` int(11) NOT NULL,
  `pozoriste_id` int(11) NOT NULL,
  `cijena` decimal(10,0) NOT NULL,
  `datum_i_vrijeme` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `karta`
--

CREATE TABLE `karta` (
  `id` int(11) NOT NULL,
  `izvodjenje_predstave_id` int(11) NOT NULL,
  `status` int(3) NOT NULL,
  `posjetilac_id` int(11) DEFAULT NULL,
  `broj_karta` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `osoblje`
--

CREATE TABLE `osoblje` (
  `id` int(11) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `tip` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `osoblje`
--

INSERT INTO `osoblje` (`id`, `ime`, `prezime`, `tip`) VALUES
(1, 'Branislav', 'Nušić', 1),
(2, 'Milica', 'Kralj', 2),
(3, 'Đorđe', 'Marković', 3),
(4, 'Svetlana', 'Bojković', 3),
(5, 'Slađana', 'Zrnić', 3),
(6, 'Danilo', 'Kerkez', 3),
(7, 'Aleksandar', 'Stojković', 3),
(8, 'Goran', 'Jokić', 3),
(9, 'Ivan', 'Plazibat', 2),
(10, 'Nataša', 'Ivančević', 3),
(11, 'Nataša', 'Perić', 3),
(12, 'Željko', 'Erkić', 3),
(13, 'Boris', 'Šavija', 3),
(14, 'Ognjen', 'Kopuz', 3),
(15, 'Anton', 'Čehov', 1),
(16, 'Marija', 'Barna-Lipkovski', 2),
(17, 'Mirjana', 'Jelić', 3),
(18, 'Đorđe', 'Živadinović', 3),
(19, 'Aleksandar', 'Jovanović', 3),
(20, 'Vilijam', 'Šekspir', 1),
(21, 'Olja', 'Đorđević', 2),
(22, 'Sonja', 'Leštar', 3),
(23, 'Dimitrije', 'Dinić', 3),
(24, 'Isidora', 'Vlček', 3),
(25, 'Milan', 'Novaković', 3),
(26, 'Molijer Žan-Batist', 'Poklen', 1),
(27, 'Igor Vuk', 'Torbica', 2),
(28, 'Saša', 'Torlaković', 3),
(29, 'Hana', 'Selimović', 3),
(30, 'Ninoslav', 'Đorđević', 3),
(31, 'Tijana', 'Marković', 3),
(32, 'Aristofan', '', 1),
(33, 'Lazar', 'Zečević', 2),
(34, 'Dragan', 'Sivić', 3),
(35, 'Srđan', 'Topalić', 3),
(36, 'Miloš', 'Durić', 3),
(37, 'Džordž', 'Etrež', 1),
(38, 'Marina', 'Simović', 3),
(39, 'Afra', 'Ben', 1),
(40, 'Tomislav', 'Drinić', 2),
(41, 'Gojko', 'Stefanović', 3),
(42, 'Nikolina', 'Simikić', 3),
(43, 'Anđelka', 'Prole', 3),
(44, 'Radovan', 'Petrov', 3),
(45, 'Dimitrije', 'Parlić', 2),
(46, 'Dušan', 'Ristić', 3),
(47, 'Božana', 'Maksimović', 3),
(48, 'Boris', 'Jovanović', 3),
(49, 'Aleksandar', 'Popovski', 2),
(50, 'Momir', 'Zarić', 3),
(51, 'Jasna', 'Đuričić', 3),
(52, 'Nikola', 'Rakočević', 3),
(53, 'Jovana', 'Stojiljković', 3),
(54, 'Snežana', 'Trišić', 2),
(55, 'Igor', 'Đorđević', 3),
(56, 'Aleksandar', 'Đurica', 3),
(57, 'Nebojša', 'Dugalić', 3),
(58, 'Vanja', 'Ejdus', 3),
(59, 'Nela', 'Mihailović', 3),
(60, 'Endru', 'Lojd-Veber', 1),
(61, 'Jug', 'Radivojević', 2),
(62, 'Marta', 'Hadžimanov', 3),
(63, 'Slobodan', 'Stefanović', 3),
(64, 'Ivan', 'Marković', 3),
(65, 'Milan', 'Antonić', 3),
(66, 'Hans Kristijan', 'Andersen', 1),
(67, 'Biserka', 'Kolevska', 2),
(68, 'Đurđa', 'Vukašinović', 3),
(69, 'Slobodan', 'Perišić', 3),
(70, 'Božana', 'Bijelić', 3),
(71, 'Aleksandar', 'Blanić', 3),
(72, 'Željko', 'Milićević', 3),
(73, 'Lajmen Frenk', 'Baum', 1),
(74, 'Staša', 'Koprivica', 2),
(75, 'Uroš', 'Jovčić', 3),
(76, 'Aleksandar', 'Milković', 3),
(77, 'Zoran', 'Cvijanović', 3),
(78, 'Nemanja', 'Oliverić', 3),
(79, 'Boris', 'Milivojević', 3),
(80, 'Nikolas Stjuart', 'Grej', 1),
(81, 'Milan', 'Karadžić', 2),
(82, 'Bojana', 'Stojković', 3),
(83, 'Đorđe', 'Kadijević', 3),
(84, 'Anja', 'Pavićević', 3),
(85, 'Džejms Metju', 'Beri', 1),
(86, 'Nikola', 'Malbaša', 3),
(87, 'Olivera', 'Bacić', 3),
(88, 'Ivana', 'Lokner', 3),
(89, 'Nenad', 'Nenadović', 3);

-- --------------------------------------------------------

--
-- Table structure for table `osoblje_predstave`
--

CREATE TABLE `osoblje_predstave` (
  `id` int(11) NOT NULL,
  `osoblje_id` int(11) NOT NULL,
  `predstava_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `osoblje_predstave`
--

INSERT INTO `osoblje_predstave` (`id`, `osoblje_id`, `predstava_id`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 1),
(6, 6, 1),
(7, 7, 1),
(8, 8, 1),
(9, 1, 2),
(10, 9, 2),
(11, 10, 2),
(12, 11, 2),
(13, 12, 2),
(14, 13, 2),
(15, 14, 2),
(16, 15, 3),
(17, 16, 3),
(18, 17, 3),
(19, 18, 3),
(20, 19, 3),
(21, 20, 4),
(22, 21, 4),
(23, 22, 4),
(24, 23, 4),
(25, 24, 4),
(26, 25, 4),
(27, 26, 5),
(28, 27, 5),
(29, 28, 5),
(30, 29, 5),
(31, 30, 5),
(32, 31, 5),
(33, 32, 6),
(34, 33, 6),
(35, 34, 6),
(36, 35, 6),
(37, 36, 6),
(38, 37, 7),
(39, 33, 7),
(40, 36, 7),
(41, 38, 7),
(42, 39, 8),
(43, 40, 8),
(44, 41, 8),
(45, 42, 8),
(46, 43, 8),
(47, 44, 8),
(48, 20, 9),
(49, 45, 9),
(50, 46, 9),
(51, 47, 9),
(52, 48, 9),
(53, 20, 10),
(54, 49, 10),
(55, 50, 10),
(56, 51, 10),
(57, 52, 10),
(58, 53, 10),
(59, 20, 11),
(60, 54, 11),
(61, 55, 11),
(62, 56, 11),
(63, 57, 11),
(64, 58, 11),
(65, 59, 11),
(66, 60, 12),
(67, 61, 12),
(68, 62, 12),
(69, 63, 12),
(70, 64, 12),
(71, 65, 12),
(72, 66, 13),
(73, 67, 13),
(74, 68, 13),
(75, 69, 13),
(76, 70, 13),
(77, 71, 13),
(78, 72, 13),
(79, 73, 14),
(80, 74, 14),
(81, 53, 14),
(82, 75, 14),
(83, 76, 14),
(84, 77, 14),
(85, 78, 14),
(86, 79, 14),
(87, 80, 15),
(88, 81, 15),
(89, 78, 15),
(90, 82, 15),
(91, 83, 15),
(92, 84, 15),
(93, 85, 16),
(94, 81, 16),
(95, 86, 16),
(96, 87, 16),
(97, 88, 16),
(98, 89, 16);

-- --------------------------------------------------------

--
-- Table structure for table `posjetilac_pozorista`
--

CREATE TABLE `posjetilac_pozorista` (
  `id` int(11) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `korisnicko_ime` varchar(45) NOT NULL,
  `lozinka` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `posjetilac_pozorista`
--

INSERT INTO `posjetilac_pozorista` (`id`, `ime`, `prezime`, `korisnicko_ime`, `lozinka`) VALUES
(1, 'Jovana', 'Jovanović', 'jovana_jovanovic', 'b3f5d95fb29b32cf1a49c3fed3ef2986'),
(2, 'Simo', 'Simic', 'simo_simic', 'b485783fcd0ef9e0a643034a60c98db9'),
(3, 'Stevan', 'Stevanovic', 'stevan_stevanovic', '00dc63516932f2a2621f55648ee4a6a9');

-- --------------------------------------------------------

--
-- Table structure for table `pozoriste`
--

CREATE TABLE `pozoriste` (
  `id` int(11) NOT NULL,
  `naziv` varchar(45) NOT NULL,
  `grad` varchar(45) NOT NULL,
  `broj_sjedista` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pozoriste`
--

INSERT INTO `pozoriste` (`id`, `naziv`, `grad`, `broj_sjedista`) VALUES
(1, 'Narodno pozorište Republike Srpske', 'Banja Luka', 318),
(2, 'Dječije pozorište Republike Srpske', 'Banja Luka', 281),
(3, 'Gradsko pozorište Jazavac', 'Banja Luka', 223);

-- --------------------------------------------------------

--
-- Table structure for table `predstava`
--

CREATE TABLE `predstava` (
  `id` int(11) NOT NULL,
  `naziv` varchar(45) NOT NULL,
  `zanr` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `predstava`
--

INSERT INTO `predstava` (`id`, `naziv`, `zanr`) VALUES
(1, 'Gospođa ministarka', 1),
(2, 'Sumnjivo lice', 1),
(3, 'Prosidba', 2),
(4, 'Ukroćena goropad', 2),
(5, 'Tartif', 3),
(6, 'Žabe', 3),
(7, 'Komična osveta', 4),
(8, 'Lutalica', 4),
(9, 'Romeo i Julija', 5),
(10, 'Hamlet', 5),
(11, 'Ričard Treći', 6),
(12, 'Fantom iz Opere', 7),
(13, 'Mala sirena', 7),
(14, 'Čarobnjak iz Oza', 7),
(15, 'Ljepotica i zvijer', 7),
(16, 'Petar Pan', 7);

-- --------------------------------------------------------

--
-- Table structure for table `radnik_pozorista`
--

CREATE TABLE `radnik_pozorista` (
  `id` int(11) NOT NULL,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `korisnicko_ime` varchar(45) NOT NULL,
  `lozinka` varchar(45) NOT NULL,
  `pozoriste_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `radnik_pozorista`
--

INSERT INTO `radnik_pozorista` (`id`, `ime`, `prezime`, `korisnicko_ime`, `lozinka`, `pozoriste_id`) VALUES
(1, 'Pero', 'Peric', 'pero_peric', 'b8fced7c9eb90e02d6130b98c05d9cf6', 1),
(2, 'Jovan', 'Jovanovic', 'jovan_jovanovic', '254c665ec089423ad5c4ff905d9c519c', 2),
(3, 'Nikola', 'Nikolic', 'nikola_nikolic', '10066c2cce5ee1e38b8d2c0752fd5e84', 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `izvodjenje_predstave`
--
ALTER TABLE `izvodjenje_predstave`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `karta`
--
ALTER TABLE `karta`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `osoblje`
--
ALTER TABLE `osoblje`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `osoblje_predstave`
--
ALTER TABLE `osoblje_predstave`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `posjetilac_pozorista`
--
ALTER TABLE `posjetilac_pozorista`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pozoriste`
--
ALTER TABLE `pozoriste`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `predstava`
--
ALTER TABLE `predstava`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `radnik_pozorista`
--
ALTER TABLE `radnik_pozorista`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `izvodjenje_predstave`
--
ALTER TABLE `izvodjenje_predstave`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `karta`
--
ALTER TABLE `karta`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `osoblje`
--
ALTER TABLE `osoblje`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=90;

--
-- AUTO_INCREMENT for table `osoblje_predstave`
--
ALTER TABLE `osoblje_predstave`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99;

--
-- AUTO_INCREMENT for table `posjetilac_pozorista`
--
ALTER TABLE `posjetilac_pozorista`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pozoriste`
--
ALTER TABLE `pozoriste`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `predstava`
--
ALTER TABLE `predstava`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `radnik_pozorista`
--
ALTER TABLE `radnik_pozorista`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
