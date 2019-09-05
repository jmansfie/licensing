DROP TABLE IF EXISTS `License`;
DROP TABLE IF EXISTS `LicenseValidationRequest`;

CREATE TABLE `License` (
  `LicenseID` int(11) NOT NULL AUTO_INCREMENT,
  `ApplicationKey` varchar(255) NOT NULL,
  `LicenseNumber` varchar(255) NOT NULL,
  `IPAddress` varchar(255) DEFAULT NULL,
  `MACAddress` varchar(255) DEFAULT NULL,
  `Hostname` varchar(255) DEFAULT NULL,
  `EffectiveDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ExpirationDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `Notes` varchar(255) DEFAULT NULL,
  `Active` tinyint(1) NOT NULL,
  `PrivateKey` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`LicenseID`)
);

CREATE TABLE `LicenseValidationRequest` (
  `LicenseValidationRequestID` int(11) NOT NULL AUTO_INCREMENT,
  `ApplicationKey` varchar(255) DEFAULT NULL,
  `LicenseNumber` varchar(255) DEFAULT NULL,
  `IPAddress` varchar(255) DEFAULT NULL,
  `MACAddress` varchar(255) DEFAULT NULL,
  `Hostname` varchar(255) DEFAULT NULL,
  `Date` timestamp NULL DEFAULT NULL,
  `Valid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`LicenseValidationRequestID`)
);
