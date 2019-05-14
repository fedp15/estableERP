package Reportes;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class DatasourceReportLibroDiario implements JRDataSource
{
    private List<DatosReportLibroDiario> listaParticipantes = new ArrayList<DatosReportLibroDiario>();
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
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNombre();
                break;
            case "totalDebe":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalDebe();
                break;
            case "totalHaber":
                valor = listaParticipantes.get(indiceParticipanteActual).getTotalHaber();
                break;
            case "ctaNivel":
                valor = listaParticipantes.get(indiceParticipanteActual).getCtaNivel();
                break;
            case "comprobante":
                valor = listaParticipantes.get(indiceParticipanteActual).getComprobante();
                break;
            case "tipDoc":
                valor = listaParticipantes.get(indiceParticipanteActual).getTipDoc();
                break;
            case "numDoc":
                valor = listaParticipantes.get(indiceParticipanteActual).getDocNum();
                break;
            case "fecha":
                valor = listaParticipantes.get(indiceParticipanteActual).getFecha();
                break;
        }

        return valor;
    }

    @Override
    public boolean next() throws JRException {
        return ++indiceParticipanteActual < listaParticipantes.size();
    }

    public void addRegistros(DatosReportLibroDiario participante)
    {
        this.listaParticipantes.add(participante);
    }
}
