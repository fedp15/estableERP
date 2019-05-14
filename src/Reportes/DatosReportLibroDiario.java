package Reportes;

/**
 *
 * @author RaaG
 */
public class DatosReportLibroDiario{
    private String cta, ctaNombre, comprobante, fecha, docNum, tipDoc, descrip;
    private int ctaNivel;
    private double totalDebe, totalHaber;

    public DatosReportLibroDiario()
    {
    }
    
    public void DatosReportLibroDiario(int ctaNivel, String cta, String ctaNombre, String fecha, 
                                       String comprobante, String docNum, String tipDoc,
                                       String descrip, Double totalDebe, Double totalHaber){
        this.ctaNivel    = ctaNivel;
        this.cta         = cta;
        this.ctaNombre   = ctaNombre;
        this.comprobante = comprobante;
        this.fecha       = fecha;
        this.docNum      = docNum;
        this.tipDoc      = tipDoc;
        this.descrip     = descrip;
        this.totalDebe   = totalDebe;
        this.totalHaber  = totalHaber;
    }

    public String getCta() {
        return cta;
    }

    public String getCtaNombre() {
        return ctaNombre;
    }

    public String getComprobante() {
        return comprobante;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDocNum() {
        return docNum;
    }

    public String getTipDoc() {
        return tipDoc;
    }

    public String getDescrip() {
        return descrip;
    }

    public int getCtaNivel() {
        return ctaNivel;
    }

    public double getTotalDebe() {
        return totalDebe;
    }

    public double getTotalHaber() {
        return totalHaber;
    }

}