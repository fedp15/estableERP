/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.math.BigDecimal;

/**
 *
 * @author RaaG
 */
public class DatosReportPOS{
    private int id;
    private String INV_NUMDOC, MAE_RIF, MAE_NOMBRE, INV_FECHA, INV_CODVEN, INV_CODPRO, PRO_NOMBRE, PRO_CODIGO;
    private String EMP_NOMBRE,EMP_RIF, rangoFch, rangoVen, rangoCaja, VEN_NOMBRE, EMP_CODE, PERS_ID, PERS_NOMBRE;
    private String CONTRI,DESDE,HASTA,filtroEmp,filtroCli,user;
    private Float IVA, DESCUENTO, SUBTOTAL, TOTAL, ENTRADAS, SALIDAS, STOCK;
    private Double COMISION,RET_IVA;
    private BigDecimal BASE_EX,BASE_IMP,IVA_,RETENCION;

    public DatosReportPOS()
    {
    }
    
    public void DatosReportVentas(int id, String numDoc, String rifCli, String nombCli, String fecha, String codVen,
                             Float iva, Float descto, Float subTotal, Float total, String nombEmp, 
                             String rifEmp, String rangoFecha, String rangoVend, String rangoCaja)
    {
        this.id = id;
        this.INV_NUMDOC = numDoc;
        this.MAE_RIF = rifCli;
        this.MAE_NOMBRE = nombCli;
        this.INV_FECHA = fecha;
        this.INV_CODVEN = codVen;
        this.IVA = iva;
        this.DESCUENTO = descto;
        this.SUBTOTAL = subTotal;
        this.TOTAL = total;
        this.EMP_NOMBRE = nombEmp;
        this.EMP_RIF = rifEmp;
        this.rangoFch = rangoFecha;
        this.rangoVen = rangoVend;
        this.rangoCaja = rangoCaja;
    }
    
    public void DatosReportStock(int id, String nomPro, String codPro, Float entradas, Float salidas, Float stock, 
                                 String nombEmp, String rifEmp, String rangoFecha)
    {
        this.id = id;
        this.PRO_NOMBRE = nomPro;
        this.INV_CODPRO = codPro;
        this.ENTRADAS   = entradas;
        this.SALIDAS    = salidas;
        this.STOCK      = stock;
        this.EMP_NOMBRE = nombEmp;
        this.EMP_RIF    = rifEmp;
        this.rangoFch = rangoFecha;
    }
    
    public void DatosReportCodBarras(int id, String codPro, String nombPro)
    {
        this.id = id;
        this.PRO_NOMBRE = nombPro;
        this.INV_CODPRO = codPro;
    }
    
    public void DatosReportComisionVendDetallado(int id,String numdoc,String fecha,Float total,Double comision,
                                                 String codigo,String nombre,String rangoVend,String rangoFecha){
        this.id = id;
        this.INV_NUMDOC = numdoc;
        this.INV_FECHA  = fecha;
        this.TOTAL      = total;
        this.COMISION   = comision;
        this.INV_CODVEN = codigo;
        this.VEN_NOMBRE = nombre;
        this.rangoVen   = rangoVend;
        this.rangoFch   = rangoFecha;
    }
     
    public void DatosReportComisionVend(int id,Float total,Double comision,String codigo,String nombre,
                                        String rangoVend,String rangoFecha){
        this.id = id;
        this.TOTAL      = total;
        this.COMISION   = comision;
        this.INV_CODVEN = codigo;
        this.VEN_NOMBRE = nombre;
        this.rangoVen   = rangoVend;
        this.rangoFch   = rangoFecha;
    }
    
    public void DatosRetIvaVta(int id,String empresa,String emp_nombre,String pers_id,
                               String pers_nombre,String contri,Double ret_iva,
                               BigDecimal base_ex,BigDecimal base_imp,BigDecimal iva,
                               BigDecimal retencion,String desde,String hasta,
                               String filEmp,String filCli,String user){
       this.id = id;
       this.EMP_CODE = empresa;
       this.EMP_NOMBRE = emp_nombre;
       this.PERS_ID = pers_id;
       this.PERS_NOMBRE = pers_nombre;
       this.CONTRI = contri;
       this.RET_IVA = ret_iva;
       this.BASE_EX = base_ex;
       this.BASE_IMP = base_imp;
       this.IVA_ = iva;
       this.RETENCION = retencion;
       this.DESDE = desde;
       this.HASTA = hasta;
       this.filtroEmp = filEmp;
       this.filtroCli = filCli;
       this.user = user;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getINV_NUMDOC() {
        return INV_NUMDOC;
    }

    public void setINV_NUMDOC(String INV_NUMDOC) {
        this.INV_NUMDOC = INV_NUMDOC;
    }

    public String getMAE_RIF() {
        return MAE_RIF;
    }

    public void setMAE_RIF(String MAE_RIF) {
        this.MAE_RIF = MAE_RIF;
    }

    public String getMAE_NOMBRE() {
        return MAE_NOMBRE;
    }

    public void setMAE_NOMBRE(String MAE_NOMBRE) {
        this.MAE_NOMBRE = MAE_NOMBRE;
    }

    public String getINV_FECHA() {
        return INV_FECHA;
    }

    public void setINV_FECHA(String INV_FECHA) {
        this.INV_FECHA = INV_FECHA;
    }

    public String getINV_CODVEN() {
        return INV_CODVEN;
    }

    public void setINV_CODVEN(String INV_CODVEN) {
        this.INV_CODVEN = INV_CODVEN;
    }

    public Float getIVA() {
        return IVA;
    }

    public void setIVA(Float IVA) {
        this.IVA = IVA;
    }

    public Float getDESCUENTO() {
        return DESCUENTO;
    }

    public void setDESCUENTO(Float DESCUENTO) {
        this.DESCUENTO = DESCUENTO;
    }

    public Float getSUBTOTAL() {
        return SUBTOTAL;
    }

    public void setSUBTOTAL(Float SUBTOTAL) {
        this.SUBTOTAL = SUBTOTAL;
    }

    public Float getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(Float TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getEMP_NOMBRE() {
        return EMP_NOMBRE;
    }

    public void setEMP_NOMBRE(String EMP_NOMBRE) {
        this.EMP_NOMBRE = EMP_NOMBRE;
    }

    public String getEMP_RIF() {
        return EMP_RIF;
    }

    public void setEMP_RIF(String EMP_RIF) {
        this.EMP_RIF = EMP_RIF;
    }

    public String getRangoFch() {
        return rangoFch;
    }

    public void setRangoFch(String rangoFch) {
        this.rangoFch = rangoFch;
    }

    public String getRangoVen() {
        return rangoVen;
    }

    public void setRangoVen(String rangoVen) {
        this.rangoVen = rangoVen;
    }

    public String getRangoCaja() {
        return rangoCaja;
    }

    public void setRangoCaja(String rangoCaja) {
        this.rangoCaja = rangoCaja;
    }

    public String getINV_CODPRO() {
        return INV_CODPRO;
    }

    public void setINV_CODPRO(String INV_CODPRO) {
        this.INV_CODPRO = INV_CODPRO;
    }

    public String getPRO_NOMBRE() {
        return PRO_NOMBRE;
    }

    public void setPRO_NOMBRE(String PRO_NOMBRE) {
        this.PRO_NOMBRE = PRO_NOMBRE;
    }

    public Float getENTRADAS() {
        return ENTRADAS;
    }

    public void setENTRADAS(Float ENTRADAS) {
        this.ENTRADAS = ENTRADAS;
    }

    public Float getSALIDAS() {
        return SALIDAS;
    }

    public void setSALIDAS(Float SALIDAS) {
        this.SALIDAS = SALIDAS;
    }

    public Float getSTOCK() {
        return STOCK;
    }

    public void setSTOCK(Float STOCK) {
        this.STOCK = STOCK;
    }

    /**
     * @return the PRO_CODIGO
     */
    public String getPRO_CODIGO() {
        return PRO_CODIGO;
    }

    /**
     * @param PRO_CODIGO the PRO_CODIGO to set
     */
    public void setPRO_CODIGO(String PRO_CODIGO) {
        this.PRO_CODIGO = PRO_CODIGO;
    }
    
    public void setCOMISION(Double comision) {
        this.COMISION = comision;
    }
    
    public Double getCOMISION() {
        return COMISION;
    }
    
    public void setVEN_NOMBRE(String nombre) {
        this.VEN_NOMBRE = nombre;
    }
    
    public String getVEN_NOMBRE() {
        return VEN_NOMBRE;
    }

    public String getEMP_CODE() {
        return EMP_CODE;
    }

    public void setEMP_CODE(String EMP_CODE) {
        this.EMP_CODE = EMP_CODE;
    }

    public String getPERS_ID() {
        return PERS_ID;
    }

    public void setPERS_ID(String PERS_ID) {
        this.PERS_ID = PERS_ID;
    }

    public String getPERS_NOMBRE() {
        return PERS_NOMBRE;
    }

    public void setPERS_NOMBRE(String PERS_NOMBRE) {
        this.PERS_NOMBRE = PERS_NOMBRE;
    }

    public String getCONTRI() {
        return CONTRI;
    }

    public void setCONTRI(String CONTRI) {
        this.CONTRI = CONTRI;
    }

    public String getDESDE() {
        return DESDE;
    }

    public void setDESDE(String DESDE) {
        this.DESDE = DESDE;
    }

    public String getHASTA() {
        return HASTA;
    }

    public void setHASTA(String HASTA) {
        this.HASTA = HASTA;
    }

    public Double getRET_IVA() {
        return RET_IVA;
    }

    public void setRET_IVA(Double RET_IVA) {
        this.RET_IVA = RET_IVA;
    }

    public BigDecimal getBASE_EX() {
        return BASE_EX;
    }

    public void setBASE_EX(BigDecimal BASE_EX) {
        this.BASE_EX = BASE_EX;
    }

    public BigDecimal getBASE_IMP() {
        return BASE_IMP;
    }

    public void setBASE_IMP(BigDecimal BASE_IMP) {
        this.BASE_IMP = BASE_IMP;
    }

    public BigDecimal getIVA_() {
        return IVA_;
    }

    public void setIVA_(BigDecimal IVA_) {
        this.IVA_ = IVA_;
    }

    public BigDecimal getRETENCION() {
        return RETENCION;
    }

    public void setRETENCION(BigDecimal RETENCION) {
        this.RETENCION = RETENCION;
    }

    public String getFiltroEmp() {
        return filtroEmp;
    }

    public void setFiltroEmp(String filtroEmp) {
        this.filtroEmp = filtroEmp;
    }

    public String getFiltroCli() {
        return filtroCli;
    }

    public void setFiltroCli(String filtroCli) {
        this.filtroCli = filtroCli;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}