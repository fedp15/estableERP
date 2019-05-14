/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Alex
 * @version 1.0
 * @author-mail programadorjavablog@gmail.com
 * @date 01-dic-2010
 */
public class DatasourceReport implements JRDataSource
{
    private List<DatosReportPOS> listaParticipantes = new ArrayList<DatosReportPOS>();
    private int indiceParticipanteActual = -1;

    public Object getFieldValue(JRField jrf) throws JRException
    {
        Object valor = null;

        if (null != jrf.getName())
        switch (jrf.getName()) {
            case "numDocumento":
                valor = listaParticipantes.get(indiceParticipanteActual).getINV_NUMDOC();
                break;
            case "idRif":
                valor = listaParticipantes.get(indiceParticipanteActual).getMAE_RIF();
                break;
            case "nombCliente":
                valor = listaParticipantes.get(indiceParticipanteActual).getMAE_NOMBRE();
                break;
            case "fecha":
                valor = listaParticipantes.get(indiceParticipanteActual).getINV_FECHA();
                break;
            case "codVend":
                valor = listaParticipantes.get(indiceParticipanteActual).getINV_CODVEN();
                break;
            case "descuento":
                valor = listaParticipantes.get(indiceParticipanteActual).getDESCUENTO();
                break;
            case "iva":
                valor = listaParticipantes.get(indiceParticipanteActual).getIVA();
                break;
            case "subTotal":
                valor = listaParticipantes.get(indiceParticipanteActual).getSUBTOTAL();
                break;
            case "total":
                valor = listaParticipantes.get(indiceParticipanteActual).getTOTAL();
                break;
            case "nombEmp":
                valor = listaParticipantes.get(indiceParticipanteActual).getEMP_NOMBRE();
                break;
            case "idRifEmp":
                valor = listaParticipantes.get(indiceParticipanteActual).getEMP_RIF();
                break;
            case "rangoFecha":
                valor = listaParticipantes.get(indiceParticipanteActual).getRangoFch();
                break;
            case "rangoVend":
                valor = listaParticipantes.get(indiceParticipanteActual).getRangoVen();
                break;
            case "rangoCaja":
                valor = listaParticipantes.get(indiceParticipanteActual).getRangoCaja();
                break;
            case "nomProduct":
                valor = listaParticipantes.get(indiceParticipanteActual).getPRO_NOMBRE();
                break;
            case "codProduct":
                valor = listaParticipantes.get(indiceParticipanteActual).getINV_CODPRO();
                break;
            case "Entradas":
                valor = listaParticipantes.get(indiceParticipanteActual).getENTRADAS();
                break;
            case "Salidas":
                valor = listaParticipantes.get(indiceParticipanteActual).getSALIDAS();
                break;
            case "Stock":
                valor = listaParticipantes.get(indiceParticipanteActual).getSTOCK();
                break;
            case "Comision":
                valor = listaParticipantes.get(indiceParticipanteActual).getCOMISION();
                break;
            case "VendNombre":
                valor = listaParticipantes.get(indiceParticipanteActual).getVEN_NOMBRE();
                break;
            case "EmpCode":
                valor = listaParticipantes.get(indiceParticipanteActual).getEMP_CODE();
                break;
            case "PersId":
                valor = listaParticipantes.get(indiceParticipanteActual).getPERS_ID();
                break;
            case "PersNomb":
                valor = listaParticipantes.get(indiceParticipanteActual).getPERS_NOMBRE();
                break;
            case "Contri":
                valor = listaParticipantes.get(indiceParticipanteActual).getCONTRI();
                break;
            case "fchDesde":
                valor = listaParticipantes.get(indiceParticipanteActual).getDESDE();
                break;
            case "fchHasta":
                valor = listaParticipantes.get(indiceParticipanteActual).getHASTA();
                break;
            case "retIva":
                valor = listaParticipantes.get(indiceParticipanteActual).getRET_IVA();
                break;
            case "baseEx":
                valor = listaParticipantes.get(indiceParticipanteActual).getBASE_EX();
                break;
            case "baseImp":
                valor = listaParticipantes.get(indiceParticipanteActual).getBASE_IMP();
                break;
            case "Iva_":
                valor = listaParticipantes.get(indiceParticipanteActual).getIVA_();
                break;
            case "retencion":
                valor = listaParticipantes.get(indiceParticipanteActual).getRETENCION();
                break;
            case "filtroEmp":
                valor = listaParticipantes.get(indiceParticipanteActual).getFiltroEmp();
                break;
            case "filtroCli":
                valor = listaParticipantes.get(indiceParticipanteActual).getFiltroCli();
                break;
            case "user":
                valor = listaParticipantes.get(indiceParticipanteActual).getUser();
                break;
        }
        return valor;
    }

    public boolean next() throws JRException
    {
        return ++indiceParticipanteActual < listaParticipantes.size();
    }

    public void addRegistros(DatosReportPOS participante)
    {
        this.listaParticipantes.add(participante);
    }
}
