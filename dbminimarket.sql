-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 01, 2024 at 11:40 AM
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
-- Database: `dbminimarket`
--

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `kd_barang` varchar(8) NOT NULL,
  `nama_barang` varchar(50) NOT NULL,
  `satuan` varchar(20) NOT NULL,
  `harga` int(20) NOT NULL,
  `stok` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kd_barang`, `nama_barang`, `satuan`, `harga`, `stok`) VALUES
('BRG001', 'Detergen 1000 gr', 'Gram', 18000, 10),
('BRG002', 'Energen 150 gr', 'Gram', 1000, 20),
('BRG003', 'Fanta 300 ml', 'Mili', 45000, 20),
('BRG004', 'Softener 900 ml', 'Mili', 10000, 50),
('BRG005', 'Autan 50 ml', 'Mili', 1000, 40),
('BRG006', 'Belagio 250 ml', 'Mili', 35000, 10),
('BRG007', 'Sunlight Lemon 200 ml', 'Mili', 2000, 20),
('BRG008', 'Rinso 900 gr', 'Gram', 10000, 50),
('BRG009', 'Molto 1500 ml', 'Mili', 36000, 10),
('BRG010', 'Rokok Samsu 234 per Pack', 'Pack', 350000, 9);

-- --------------------------------------------------------

--
-- Table structure for table `penjualan`
--

CREATE TABLE `penjualan` (
  `id_transaksi` int(20) NOT NULL,
  `no_faktur` varchar(8) NOT NULL,
  `tanggal` date NOT NULL,
  `kd_barang` varchar(8) NOT NULL,
  `id_user` varchar(8) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `sub_total` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `penjualan`
--

INSERT INTO `penjualan` (`id_transaksi`, `no_faktur`, `tanggal`, `kd_barang`, `id_user`, `jumlah`, `sub_total`) VALUES
(2, 'NFS00001', '2024-06-14', 'BRG002', 'ID004', 5, 5000),
(9, 'NFS00002', '2024-06-20', 'BRG010', 'ID004', 1, 350000),
(10, 'NFS00003', '2024-06-20', 'BRG010', 'ID004', 1, 350000),
(11, 'NFS00004', '2024-06-21', 'BRG005', 'ID004', 10, 10000),
(12, 'NFS00005', '2024-06-21', 'BRG010', 'ID004', 2, 700000);

-- --------------------------------------------------------

--
-- Stand-in structure for view `relasidatatransaksi`
-- (See below for the actual view)
--
CREATE TABLE `relasidatatransaksi` (
`no_faktur` varchar(8)
,`tanggal` date
,`id_user` varchar(8)
,`nama` varchar(25)
,`status` varchar(15)
,`username` varchar(20)
,`akses` varchar(20)
,`kd_barang` varchar(8)
,`nama_barang` varchar(50)
,`satuan` varchar(20)
,`harga` int(20)
,`stok` int(11)
,`jumlah` int(11)
,`sub_total` int(20)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `relasikeranjang`
-- (See below for the actual view)
--
CREATE TABLE `relasikeranjang` (
`no_faktur` varchar(8)
,`tanggal` date
,`id_user` varchar(8)
,`username` varchar(20)
,`akses` varchar(20)
,`kd_barang` varchar(8)
,`nama_barang` varchar(50)
,`harga` int(20)
,`jumlah` int(11)
,`sub_total` int(20)
);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` varchar(8) NOT NULL,
  `nama` varchar(25) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `telpon` varchar(15) NOT NULL,
  `status` varchar(15) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `akses` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `alamat`, `telpon`, `status`, `username`, `password`, `akses`) VALUES
('ID001', 'Muhammad Siddik', 'Jl. Purwosari No.40 F', '082284914949', 'Aktif', 'siddik', 'siddik123', 'Admin'),
('ID002', 'Leni Marlina', 'Jl. Mawar Indah No.76', '08227464535', 'Aktif', 'leni', 'leni123', 'Kasir 1'),
('ID003', 'Ridwan Darmawan', 'Jl. Kemuning No.87', '081276549473', 'Aktif', 'ridwan', 'ridwan123', 'Kasir 2'),
('ID004', 'Susanti', 'Jl. Lumba-lumba No.89', '08127683937', 'Aktif', 'santi', 'santi123', 'Kasir 3'),
('ID005', 'Falah Subakti', 'Jl. Durian No.73 A', '081276836359', 'Aktif', 'falah', 'falah123', 'Kasir 4');

-- --------------------------------------------------------

--
-- Structure for view `relasidatatransaksi`
--
DROP TABLE IF EXISTS `relasidatatransaksi`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `relasidatatransaksi`  AS SELECT `penjualan`.`no_faktur` AS `no_faktur`, `penjualan`.`tanggal` AS `tanggal`, `user`.`id_user` AS `id_user`, `user`.`nama` AS `nama`, `user`.`status` AS `status`, `user`.`username` AS `username`, `user`.`akses` AS `akses`, `barang`.`kd_barang` AS `kd_barang`, `barang`.`nama_barang` AS `nama_barang`, `barang`.`satuan` AS `satuan`, `barang`.`harga` AS `harga`, `barang`.`stok` AS `stok`, `penjualan`.`jumlah` AS `jumlah`, `penjualan`.`sub_total` AS `sub_total` FROM ((`penjualan` join `user`) join `barang`) WHERE `penjualan`.`id_user` = `user`.`id_user` AND `penjualan`.`kd_barang` = `barang`.`kd_barang` GROUP BY `penjualan`.`no_faktur`, `penjualan`.`tanggal`, `penjualan`.`id_user`, `penjualan`.`kd_barang` ;

-- --------------------------------------------------------

--
-- Structure for view `relasikeranjang`
--
DROP TABLE IF EXISTS `relasikeranjang`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `relasikeranjang`  AS SELECT `penjualan`.`no_faktur` AS `no_faktur`, `penjualan`.`tanggal` AS `tanggal`, `user`.`id_user` AS `id_user`, `user`.`username` AS `username`, `user`.`akses` AS `akses`, `barang`.`kd_barang` AS `kd_barang`, `barang`.`nama_barang` AS `nama_barang`, `barang`.`harga` AS `harga`, `penjualan`.`jumlah` AS `jumlah`, `penjualan`.`sub_total` AS `sub_total` FROM ((`penjualan` join `user`) join `barang`) WHERE `penjualan`.`id_user` = `user`.`id_user` AND `penjualan`.`kd_barang` = `barang`.`kd_barang` GROUP BY `penjualan`.`no_faktur`, `penjualan`.`tanggal`, `penjualan`.`id_user`, `penjualan`.`kd_barang` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kd_barang`);

--
-- Indexes for table `penjualan`
--
ALTER TABLE `penjualan`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `penjualan`
--
ALTER TABLE `penjualan`
  MODIFY `id_transaksi` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
