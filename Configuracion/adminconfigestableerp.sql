CREATE TABLE `dnconexion` (
   `conf_id` INT(11) NOT NULL AUTO_INCREMENT,
   `conf_ippc` VARCHAR(15) NULL DEFAULT NULL,
   `conf_macpc` VARCHAR(20) NULL DEFAULT NULL,
   `conf_ip` VARCHAR(20) NULL DEFAULT NULL,
   `conf_user` VARCHAR(15) NULL DEFAULT NULL,
   `conf_pass` VARCHAR(30) NULL DEFAULT NULL,
   `conf_bd` VARCHAR(30) NULL DEFAULT NULL,
   `conf_manejador` VARCHAR(30) NULL DEFAULT NULL,
   `conf_fchconf` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `conf_activo` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`conf_id`)) COMMENT='Tabla de Configuraci+Ã¢Ã£Ã†+Ã©-Â¦n de Conexiones' COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;


CREATE TABLE `dnpais` (
   `pai_empresa` VARCHAR(6) NULL DEFAULT NULL,
   `pai_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Campo de ID de la Tabla',
   `pai_dattim` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Campo de Fecha y Hora de Creacion del Registro',
   `pai_user` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Usuario que Ingreso el Registro',
   `pai_macpc` VARCHAR(20) NULL DEFAULT NULL COMMENT 'Mac Address del Equipo que incluyo el Registro',
   `pai_nombre` VARCHAR(60) NULL DEFAULT NULL,
   `pai_contin` VARCHAR(30) NULL DEFAULT NULL,
   `pai_abrpai` VARCHAR(3) NULL DEFAULT NULL,
   `pai_activo` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`pai_id`),
   INDEX `fk_dnpais_dnempresas` (`pai_empresa`)) COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;

CREATE TABLE `dnestados` (
   `est_empresa` VARCHAR(6) NULL DEFAULT NULL,
   `est_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Campo de ID de la Tabla',
   `est_dattim` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Campo de Fecha y Hora de Creacion del Registro',
   `est_user` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Usuario que Ingreso el Registro',
   `est_macpc` VARCHAR(20) NULL DEFAULT NULL COMMENT 'Mac Address del Equipo que incluyo el Registro',
   `est_codpai` INT(11) NULL DEFAULT NULL,
   `est_nombre` VARCHAR(60) NULL DEFAULT NULL,
   `codigo` INT(2) NULL DEFAULT NULL,
   `est_activo` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`est_id`),
   INDEX `fk_dnestados_dnempresas` (`est_empresa`),
   INDEX `fk_dnestados_dnpais` (`est_codpai`),
   CONSTRAINT `fk_dnestados_dnpais` FOREIGN KEY (`est_codpai`) REFERENCES `dnpais` (`pai_id`)) COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;

CREATE TABLE `dnmunicipios` (
   `mun_empresa` VARCHAR(6) NOT NULL,
   `mun_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Campo de ID de la Tabla',
   `mun_dattim` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Campo de Fecha y Hora de Creacion del Registro',
   `mun_user` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Usuario que Ingreso el Registro',
   `mun_macpc` VARCHAR(20) NULL DEFAULT NULL COMMENT 'Mac Address del Equipo que incluyo el Registro',
   `mun_codest` INT(11) NOT NULL,
   `mun_nombre` VARCHAR(60) NULL DEFAULT NULL,
   `codigo` VARCHAR(2) NULL DEFAULT NULL,
   `mun_activo` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`mun_id`),
   INDEX `fk_dnmunicipios_dnempresas` (`mun_empresa`),
   INDEX `fk_dnmunicipios_dnestados` (`mun_codest`),
   CONSTRAINT `fk_dnmunicipios_dnestados` FOREIGN KEY (`mun_codest`) REFERENCES `dnestados` (`est_id`)) COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;

CREATE TABLE `dnparroquias` (
   `par_empresa` VARCHAR(6) NULL DEFAULT NULL,
   `par_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Campo de ID de la Tabla',
   `par_dattim` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Campo de Fecha y Hora de Creacion del Registro',
   `par_user` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Usuario que Ingreso el Registro',
   `par_macpc` VARCHAR(20) NULL DEFAULT NULL COMMENT 'Mac Address del Equipo que incluyo el Registro',
   `par_nombre` VARCHAR(60) NULL DEFAULT NULL,
   `codigo` INT(2) UNSIGNED ZEROFILL NULL DEFAULT NULL,
   `par_codmun` INT(11) NULL DEFAULT NULL,
   `par_activo` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`par_id`),
   INDEX `fk_dnparroquias_dnempresas` (`par_empresa`),
   INDEX `fk_dnparroquias_dnmunicipios` (`par_codmun`),
   CONSTRAINT `fk_dnparroquias_dnmunicipios` FOREIGN KEY (`par_codmun`) REFERENCES `dnmunicipios` (`mun_id`)) COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;

CREATE TABLE `dnsector` (
   `sbs_empresa` VARCHAR(6) NOT NULL,
   `sbs_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Campo de ID de la Tabla',
   `sbs_dattim` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Campo de Fecha y Hora de Creacion del Registro',
   `sbs_user` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Usuario que Ingreso el Registro',
   `sbs_macpc` VARCHAR(20) NULL DEFAULT NULL COMMENT 'Mac Address del Equipo que incluyo el Registro',
   `sbs_codpar` INT(11) NULL DEFAULT NULL COMMENT 'Campo de ID de la Tabla',
   `sbs_nombre` VARCHAR(60) NULL DEFAULT NULL,
   `codigo` INT(2) UNSIGNED ZEROFILL NULL DEFAULT NULL,
   `sbs_activo` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`sbs_id`),
   INDEX `fk_dnsubsector_dnempresas` (`sbs_empresa`),
   INDEX `fk_dnsubsector_dnparroquia` (`sbs_codpar`),
   CONSTRAINT `fk_dnsubsector_dnparroquia` FOREIGN KEY (`sbs_codpar`) REFERENCES `dnparroquias` (`par_id`)) COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;

CREATE TABLE `dnempresas` (
   `emp_dattim` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `emp_user` VARCHAR(10) NULL DEFAULT NULL,
   `emp_macpc` VARCHAR(20) NULL DEFAULT NULL,
   `emp_codigo` VARCHAR(6) NOT NULL,
   `base_datos_empresa` VARCHAR(255) NULL DEFAULT NULL,
   `emp_nombre` VARCHAR(255) NULL DEFAULT NULL,
   `emp_rif` VARCHAR(12) NOT NULL,
   `emp_direccion` TEXT NULL,
   `emp_telefono` VARCHAR(255) NULL DEFAULT NULL,
   `emp_fax` VARCHAR(255) NULL DEFAULT NULL,
   `emp_correo` VARCHAR(255) NULL DEFAULT NULL,
   `emp_moneda` VARCHAR(25) NOT NULL DEFAULT ' ',
   `emp_tipoidentificador` INT(10) NULL DEFAULT NULL,
   `emp_paginaweb` VARCHAR(255) NULL DEFAULT NULL,
   `emp_pais` VARCHAR(125) NULL DEFAULT NULL,
   `emp_estado` INT(11) NULL DEFAULT NULL,
   `emp_municipio` INT(11) NULL DEFAULT NULL,
   `emp_parroquia` INT(11) NULL DEFAULT NULL,
   `emp_sector` INT(11) NULL DEFAULT NULL,
   `emp_activo` TINYINT(1) NULL DEFAULT NULL,
   `emp_moditem` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para Modificar Item',
   `emp_remitem` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para Remover item',
   `emp_recfac` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para Recupera Fac',
   `emp_descdir` TINYINT(1) NULL DEFAULT NULL COMMENT 'CLave para Descuento directo',
   `emp_facpen` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para Factura Pendiente',
   `emp_nostock` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para Facturar sin stock',
   `emp_nojornada` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para Iniciar sesion fuera de la Jornada',
   `emp_sesion` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para mantener sesion activa pasada la hora final del turno',
   `emp_apert` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para iniciar sesion sin apertura de caja',
   `emp_selpre` TINYINT(1) NULL DEFAULT NULL COMMENT 'Clave para cambiar precio',
   `emp_welcome` TINYINT(1) NULL DEFAULT NULL,
   `emp_empresa` VARCHAR(6) NULL DEFAULT NULL,
   `pers_id` INT(11) NULL DEFAULT NULL,
   `emp_retpag` TINYINT(1) NULL DEFAULT '0',
   `emp_predeterminada` TINYINT(1) NULL DEFAULT '0',
   `nombre_foto_logo` VARCHAR(250) NULL DEFAULT ' ',
   `imagen` MEDIUMBLOB NULL,
   PRIMARY KEY (`emp_codigo`, `emp_rif`),
   INDEX `emp_codigo` (`emp_codigo`),
   INDEX `fk_fkdnempresas_dnpersonas` (`pers_id`),
   INDEX `emp_estado` (`emp_estado`),
   INDEX `emp_municipio` (`emp_municipio`),
   INDEX `emp_parroquia` (`emp_parroquia`),
   INDEX `emp_sector` (`emp_sector`),
   INDEX `emp_tipoidentificador` (`emp_tipoidentificador`),
   CONSTRAINT `fk_dnempresas_dnestados` FOREIGN KEY (`emp_estado`) REFERENCES `dnestados` (`est_id`),
   CONSTRAINT `fk_dnempresas_dnmunicipios` FOREIGN KEY (`emp_municipio`) REFERENCES `dnmunicipios` (`mun_id`),
   CONSTRAINT `fk_dnempresas_dnparroquias` FOREIGN KEY (`emp_parroquia`) REFERENCES `dnparroquias` (`par_id`),
   CONSTRAINT `fk_dnempresas_dnsector` FOREIGN KEY (`emp_sector`) REFERENCES `dnsector` (`sbs_id`)) COMMENT='Tablas para Multi-Empresas' COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `dncargos` (
   `car_empresa` VARCHAR(6) NULL DEFAULT NULL,
   `car_id` INT(11) NOT NULL AUTO_INCREMENT,
   `car_user` VARCHAR(10) NULL DEFAULT NULL,
   `car_macpc` VARCHAR(20) NULL DEFAULT NULL,
   `car_dattim` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `car_codigo` VARCHAR(10) NOT NULL,
   `car_descri` VARCHAR(80) NULL DEFAULT NULL,
   `car_id_person` INT(11) NULL DEFAULT NULL,
   `car_img` VARCHAR(60) NULL DEFAULT NULL,
   `car_activo` TINYINT(1) NULL DEFAULT NULL,
   `car_orden` INT(11) NULL DEFAULT NULL,
   `car_url` VARCHAR(60) NULL DEFAULT NULL,
   PRIMARY KEY (`car_id`),
   INDEX `car_empresa` (`car_empresa`),
   INDEX `fk_dnrel_recur_cargo_dnpersonas` (`car_id_person`),
   CONSTRAINT `dncargos_ibfk_1` FOREIGN KEY (`car_empresa`) REFERENCES `dnempresas` (`emp_codigo`)) COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;

CREATE TABLE `dnrol` (
   `rol_id` INT(11) NOT NULL AUTO_INCREMENT,
   `rol_nombre` VARCHAR(80) NULL DEFAULT NULL,
   `rol_id_cargo` INT(11) NULL DEFAULT NULL,
   PRIMARY KEY (`rol_id`),
   INDEX `fk_drol_dncargos` (`rol_id_cargo`),
   CONSTRAINT `fk_drol_dncargos` FOREIGN KEY (`rol_id_cargo`) REFERENCES `dncargos` (`car_id`)) COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;

CREATE TABLE `type_person` (
   `id_type_person` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(120) NOT NULL,
   `short_name` VARCHAR(1) NOT NULL,
   `pais` VARCHAR(125) NULL DEFAULT NULL,
   `id_rol` INT(11) NULL DEFAULT NULL,
   `active` INT(11) NOT NULL,
   PRIMARY KEY (`id_type_person`),
   INDEX `fk_type_person` (`id_rol`),
   CONSTRAINT `fk_type_person` FOREIGN KEY (`id_rol`) REFERENCES `dnrol` (`rol_id`)) COLLATE='utf8_general_ci' 
ENGINE=InnoDB
AUTO_INCREMENT=0;

CREATE TABLE `dnmoneda` (
   `mon_empresa` VARCHAR(6) NULL DEFAULT NULL,
   `mon_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Campo de ID de la Tabla',
   `mon_dattim` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Campo de Fecha y Hora de Creacion del Registro',
   `mon_user` VARCHAR(10) NULL DEFAULT NULL COMMENT 'Usuario que Ingreso el Registro',
   `mon_macpc` VARCHAR(20) NULL DEFAULT NULL COMMENT 'Mac Address del Equipo que incluyo el Registro',
   `mon_codigo` VARCHAR(6) NOT NULL,
   `mon_nombre` VARCHAR(40) NULL DEFAULT NULL,
   `mon_nomenc` VARCHAR(6) NOT NULL DEFAULT ' ',
   `mon_valor` DECIMAL(8,2) NULL DEFAULT NULL,
   `mon_multi_div` VARCHAR(1) NULL DEFAULT NULL,
   `mon_cod_iso` VARCHAR(3) NULL DEFAULT NULL,
   `mon_fchvig` DATE NULL DEFAULT NULL,
   `mon_activo` TINYINT(1) NULL DEFAULT NULL,
   PRIMARY KEY (`mon_id`),
   INDEX `mon_codigo` (`mon_codigo`)) COMMENT='Tabla de Monedas' COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0;

-- Volcando estructura para procedimiento bd_don_miguel_ultima.sp_formEmpresas
DROP PROCEDURE IF EXISTS `sp_formEmpresas`;

DELIMITER //
CREATE DEFINER=`root`@`%` PROCEDURE `sp_formEmpresas`(
	IN `operacion` VARCHAR(50),
	IN `codEmp` VARCHAR(6),
	IN `idUser` VARCHAR(10),
	IN `macPc` VARCHAR(20),
	IN `nombre` VARCHAR(255),
	IN `rif` VARCHAR(12),
	IN `direccion` TEXT,
	IN `activo` VARCHAR(1),
	IN `telefono` VARCHAR(255),
	IN `correo` VARCHAR(120),
	IN `moneda` VARCHAR(25),
	IN `pais` VARCHAR(120),
	IN `fax` VARCHAR(255),
	IN `paginaWeb` VARCHAR(255),
	IN `tipoIdentificador` VARCHAR(50),
	IN `estado` VARCHAR(50),
	IN `municipio` VARCHAR(50),
	IN `parroquia` VARCHAR(50),
	IN `sector` VARCHAR(50),
	IN `empPredeterminada` VARCHAR(1)
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	DECLARE getidTipoIdentidad VARCHAR(10);
	DECLARE getidEstado VARCHAR(10);
	DECLARE getidMunicipio VARCHAR(10);
	DECLARE getidParroquia VARCHAR(10);
	DECLARE getidSector VARCHAR(10);
	DECLARE baseDatosEmpresa VARCHAR(255);
	
	IF (tipoIdentificador!='') THEN
		SET getidTipoIdentidad = (SELECT type_person.id_type_person FROM type_person 
			                       WHERE type_person.name=tipoIdentificador AND type_person.id_rol=3);
	ELSE
		SET getidTipoIdentidad = null;
	END IF;
	
	IF (estado!='') THEN
		SET getidEstado = (SELECT dnestados.est_id FROM dnestados WHERE dnestados.est_id=estado OR dnestados.est_nombre=estado);
	ELSE
		SET getidEstado = null;
	END IF;
	
	IF (municipio!='') THEN
		SET getidMunicipio = (SELECT dnmunicipios.mun_id FROM dnmunicipios WHERE dnmunicipios.mun_id=municipio OR dnmunicipios.mun_nombre=municipio);
	ELSE
		SET getidMunicipio = null;
	END IF;
	
	IF (parroquia!='') THEN
		SET getidParroquia = (SELECT dnparroquias.par_id FROM dnparroquias WHERE dnparroquias.par_id=parroquia OR dnparroquias.par_nombre=parroquia);
	ELSE
		SET getidParroquia = null;
	END IF;
	
	IF (sector!='') THEN
		SET getidSector = (SELECT dnsector.sbs_id FROM dnsector WHERE dnsector.sbs_id=sector OR dnsector.sbs_nombre=sector);
	ELSE
		SET getidSector = null;
	END IF;
	
	IF (operacion='SELECT_EMP_PREDETERMINADA') THEN
		SELECT emp_codigo, CONCAT(EMP_CODIGO,' - ',EMP_NOMBRE) AS codigo_nombre FROM dnempresas WHERE EMP_ACTIVO=1 AND emp_predeterminada=1;
	END IF;
	
	IF (operacion='COUNT EMP_PREDETERMINADA') THEN
		SELECT COUNT(*) AS REGISTROS FROM dnempresas WHERE emp_predeterminada=1;
	END IF;
	
	IF (operacion='GET_EMP_PREDETERMINADA') THEN
		SELECT emp_codigo FROM dnempresas WHERE EMP_ACTIVO=1 AND emp_predeterminada=1;
	END IF;
	
	IF (operacion='SELECT') THEN
		IF (codEmp='') THEN
			SELECT emp_codigo, base_datos_empresa FROM dnempresas ORDER BY EMP_CODIGO DESC LIMIT 1;
		ELSE
			SELECT emp_codigo,emp_nombre,emp_rif,emp_direccion,emp_telefono,emp_fax,emp_correo,emp_paginaweb,emp_moneda,
			       CONCAT(mon_nomenc,' - ',mon_nombre) as moneda,
					 (SELECT name FROM type_person WHERE type_person.id_type_person=emp_tipoidentificador AND type_person.id_rol=3) AS emp_tipoidentificador,
					 emp_pais,
					 (SELECT CONCAT(est_nombre,' - ',est_id) FROM dnestados WHERE dnestados.est_id=emp_estado) AS emp_estado,
					 (SELECT CONCAT(mun_nombre,' - ',mun_id) FROM dnmunicipios WHERE mun_id=emp_municipio) AS emp_municipio,
					 (SELECT CONCAT(par_nombre,' - ',par_id) FROM dnparroquias WHERE par_id=emp_parroquia) AS emp_parroquia,
					 IF(ISNULL((SELECT CONCAT(sbs_nombre,' - ',sbs_id) FROM dnsector WHERE sbs_id=emp_sector)),'',
					    (SELECT CONCAT(sbs_nombre,' - ',sbs_id) FROM dnsector WHERE sbs_id=emp_sector)) AS emp_sector,emp_activo,emp_predeterminada,
					 base_datos_empresa
			FROM dnempresas 
			LEFT JOIN dnmoneda ON mon_nomenc=emp_moneda
			WHERE emp_codigo=codEmp;
		END IF;
	END iF;

	IF (operacion='INSERT') THEN
      SET baseDatosEmpresa = CONCAT('estableerp_',codEmp);
      
		INSERT INTO dnempresas (EMP_USER,EMP_MACPC,EMP_CODIGO,EMP_NOMBRE,EMP_RIF, EMP_DIRECCION,emp_telefono,emp_fax,emp_correo,emp_paginaweb,
 		                        emp_moneda,emp_tipoidentificador,emp_pais,emp_estado,emp_municipio,emp_parroquia,emp_sector,EMP_ACTIVO,
										EMP_RECFAC,EMP_FACPEN,EMP_SESION,EMP_APERT,EMP_SELPRE,EMP_WELCOME,emp_predeterminada, base_datos_empresa)
                      VALUES (idUser,macPc,codEmp,nombre,rif,direccion,telefono,fax,correo,paginaWeb,moneda,getidTipoIdentidad,pais,
							 			getidEstado,getidMunicipio,getidParroquia,getidSector,activo,1,1,0,0,0,1,empPredeterminada, baseDatosEmpresa);
	END iF;
	
	IF (operacion='UPDATE') THEN
		UPDATE DNEMPRESAS SET emp_nombre=nombre,
									 emp_rif=rif,
									 emp_direccion=direccion,
									 emp_telefono=telefono,
									 emp_fax=fax,
		                      emp_correo=correo,
									 emp_paginaweb=paginaWeb,
									 emp_moneda=moneda,
									 emp_tipoidentificador=getidTipoIdentidad,
									 emp_pais=pais,
									 emp_estado=getidEstado,
									 emp_municipio=getidMunicipio,
									 emp_parroquia=getidParroquia,
									 emp_sector=getidSector,
									 emp_activo=activo,
									 emp_predeterminada=empPredeterminada
      WHERE EMP_CODIGO=codEmp;
	END iF;
	
	IF (operacion='UPDATE PREDETERMINADA') THEN
		UPDATE DNEMPRESAS SET emp_predeterminada=0;
      
		UPDATE DNEMPRESAS SET emp_predeterminada=empPredeterminada
      WHERE EMP_CODIGO=codEmp;
	END IF;
	
	IF (operacion='DELETE') THEN
		DELETE FROM dnempresas WHERE emp_codigo=codEmp;
	END iF;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS `sp_formMoneda`;

DELIMITER //
CREATE DEFINER=`root`@`%` PROCEDURE `sp_formMoneda`(
	IN operacion VARCHAR(6),
	IN codEmp VARCHAR(6),
	IN idUser VARCHAR(10),
	IN macPc VARCHAR(20),
	IN codMoneda VARCHAR(12),
	IN nombre VARCHAR(40),
	IN simbolo VARCHAR(6),
	IN valor VARCHAR(50),
	IN multi_div VARCHAR(1),
	IN cod_iso VARCHAR(3),
	IN fecha VARCHAR(10),
	IN activo TINYINT)
BEGIN 
	DECLARE countMoneda INT;
/*
	IF (operacion='SELECT') THEN

		IF (codEmp='') THEN
			SELECT mod_codigo FROM dnmoneda ORDER BY mod_codigo DESC LIMIT 1;
		ELSE
			SELECT emp_codigo,emp_nombre,emp_rif,emp_direccion,emp_telefono,emp_correo,emp_moneda,emp_pais,emp_activo 
			FROM dnmoneda WHERE emp_codigo=codEmp;
		END IF;
	END iF;
*/
	IF (operacion='INSERT') THEN
		SET countMoneda = (SELECT count(*) FROM dnmoneda WHERE MON_EMPRESA=codEmp AND mon_cod_iso=cod_iso);
		
		IF (countMoneda=0) THEN
      	INSERT INTO dnmoneda (MON_EMPRESA,MON_USER,MON_MACPC,MON_CODIGO,MON_NOMBRE,MON_NOMENC,mon_multi_div,MON_VALOR,
         	                   mon_cod_iso,MON_FCHVIG,MON_ACTIVO) 
            	        VALUES (codEmp,idUser,macPc,codMoneda,nombre,simbolo,multi_div,valor,cod_iso,fecha,activo);
      END IF;
	END iF;
	
	IF (operacion='UPDATE') THEN
      UPDATE dnmoneda SET MON_NOMBRE=nombre,MON_NOMENC=simbolo,MON_VALOR=valor,mon_multi_div=multi_div,
		                    mon_cod_iso=cod_iso,MON_FCHVIG=fecha,MON_ACTIVO=activo
      WHERE MON_EMPRESA=codEmp AND MON_CODIGO=codMoneda;
	END iF;
	
	IF (operacion='DELETE') THEN
		DELETE FROM dnmoneda WHERE mod_empresa=codEmp AND mod_codigo=codMoneda;
	END iF;
END//
DELIMITER ;