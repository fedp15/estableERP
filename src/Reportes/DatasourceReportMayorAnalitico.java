package Reportes;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class DatasourceReportMayorAnalitico implements JRDataSource
{
    private List<DatosReportMayorAnalico> listaParticipantes = new ArrayList<DatosReportMayorAnalico>();
    //private List<DatosReportMayorAnalicoDetalle> listaMayoDetalle = new ArrayList<DatosReportMayorAnalicoDetalle>();
    private int indiceParticipanteActual = -1;

    public Object getFieldValue(JRField jrf) throws JRException
    {
        Object valor = null;

        if (null != jrf.getName())
        switch (jrf.getName()) {
            case "cuenta":
                valor = listaParticipantes.get(indiceParticipanteActual).getCta();
                break;
            case "cta_nombre":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNOmbre();
                break;
            case "ctaNivel":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel();
                break;
            case "total":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotal();
                break;
            case "cta_nivel_1":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel1();
                break;
            case "cta_nivel_2":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel2();
                break;
            case "cta_nivel_3":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel3();
                break;
            case "cta_nivel_4":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel4();
                break;
            case "cta_nivel_5":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel5();
                break;
            case "cta_nivel_6":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel6();
                break;
            case "cta_nivel_7":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel7();
                break;
            case "cta_nivel_8":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel8();
                break;
            case "total_nivel_1":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalNivel1();
                break;
            case "total_nivel_2":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalNivel2();
                break;
            case "total_nivel_3":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalNivel3();
                break;
            case "total_nivel_4":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalNivel4();
                break;
            case "total_nivel_5":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalNivel5();
                break;
            case "total_nivel_6":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalNivel6();
                break;
            case "total_nivel_7":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalNivel7();
                break;
            //case "total_nivel_8":
            //    valor = listaParticipantes.get(indiceParticipanteActual).getTotalNivel8();
            //    break;
            case "descripTotal_nivel_1":
                valor = listaParticipantes.get(indiceParticipanteActual).getDescripTotal_nivel_1();
                break;
            case "descripTotal_nivel_2":
                valor = listaParticipantes.get(indiceParticipanteActual).getDescripTotal_nivel_2();
                break;
            case "descripTotal_nivel_3":
                valor = listaParticipantes.get(indiceParticipanteActual).getDescripTotal_nivel_3();
                break;
            case "descripTotal_nivel_4":
                valor = listaParticipantes.get(indiceParticipanteActual).getDescripTotal_nivel_4();
                break;
            case "descripTotal_nivel_5":
                valor = listaParticipantes.get(indiceParticipanteActual).getDescripTotal_nivel_5();
                break;
            case "descripTotal_nivel_6":
                valor = listaParticipantes.get(indiceParticipanteActual).getDescripTotal_nivel_6();
                break;
            case "descripTotal_nivel_7":
                valor = listaParticipantes.get(indiceParticipanteActual).getDescripTotal_nivel_7();
                break;
            //case "descripTotal_nivel_8":
            //    valor = listaParticipantes.get(indiceParticipanteActual).getDescripTotal_nivel_8();
            //    break;
            case "cta_nombre_nivel_1":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombreNivel1();
                break;
            case "cta_nombre_nivel_2":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombreNivel2();
                break;
            case "cta_nombre_nivel_3":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombreNivel3();
                break;
            case "cta_nombre_nivel_4":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombreNivel4();
                break;
            case "cta_nombre_nivel_5":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombreNivel5();
                break;
            case "cta_nombre_nivel_6":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombreNivel6();
                break;
            case "cta_nombre_nivel_7":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombreNivel7();
                break;
            case "cta_nombre_nivel_8":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombreNivel8();
                break;
            case "saldoInicial":
                valor = listaParticipantes.get(indiceParticipanteActual).getSaldoInicial();
                break;
            case "totalDebe":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalDebe();
                break;
            case "totalHaber":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalHaber();
                break;
            case "comprobante":
                valor = listaParticipantes.get(indiceParticipanteActual).getComprobante();
                break;
            case "fechaComprobante":
                valor = listaParticipantes.get(indiceParticipanteActual).getFecha();
                break;
            case "numdoc":
                valor = listaParticipantes.get(indiceParticipanteActual).getDoc();
                break;
            case "descrip":
                valor = listaParticipantes.get(indiceParticipanteActual).getDescrip();
                break;
            case "saldoInicialDet":
                valor = listaParticipantes.get(indiceParticipanteActual).getSaldoInicialDet();
                break;
            case "totalDebeDet":
                valor = listaParticipantes.get(indiceParticipanteActual).getDebeDetalle();
                break;
            case "totalHaberDet":
                valor = listaParticipantes.get(indiceParticipanteActual).getHaberDetelle();
                break;
            case "saldoFinalDet":
                valor = listaParticipantes.get(indiceParticipanteActual).getSaldoFinalDet();
                break;
            case "totalActivos":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalActivos();
                break;
            case "totalPasivoCapital":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalPasivoCapital();
                break;
            case "totalEgresoIngresos":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalEgresoIngresos();
                break;
        }

        return valor;
    }

    @Override
    public boolean next() throws JRException {
        return ++indiceParticipanteActual < listaParticipantes.size();
    }

    public void addRegistros(DatosReportMayorAnalico participante)
    {
        this.listaParticipantes.add(participante);
    }
    
    //public void addRegistros(DatosReportMayorAnalicoDetalle participante)
    //{
    //    this.listaMayoDetalle.add(participante);
    //}
}