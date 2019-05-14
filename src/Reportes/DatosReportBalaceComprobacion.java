package Reportes;

/**
 *
 * @author RaaG
 */
public class DatosReportBalaceComprobacion{
    private String cta, ctaNOmbre, ctaNivel1, ctaNivel2, ctaNivel3, ctaNivel4, 
                   ctaNivel5, ctaNivel6, ctaNivel7, ctaNivel8, totalNivel1, 
                   totalNivel2, totalNivel3, totalNivel4,totalNivel5, 
                   totalNivel6, totalNivel7, totalNivel8, descripTotal_nivel_1,
                   descripTotal_nivel_2, descripTotal_nivel_3, descripTotal_nivel_4,
                   descripTotal_nivel_5, descripTotal_nivel_6, descripTotal_nivel_7,
                   descripTotal_nivel_8, ctaNombreNivel1, ctaNombreNivel2, ctaNombreNivel3,
                   ctaNombreNivel4, ctaNombreNivel5, ctaNombreNivel6, ctaNombreNivel7,
                   ctaNombreNivel8;
    private int ctaNivel;
    private Double saldoInicial, totalDebe, totalHaber, total, totalActivos, 
                   totalPasivoCapital, totalEgresoIngresos;

    public DatosReportBalaceComprobacion()
    {
    }
    
    public void DatosReportVentas(int ctaNivel, String cta, String ctaNOmbre, String ctaNivel1, 
                                  String ctaNivel2, String ctaNivel3, String ctaNivel4, String ctaNivel5,
                                  String ctaNivel6, String ctaNivel7, String ctaNivel8, Double total,
                                  String totalNivel1, String totalNivel2, String totalNivel3,
                                  String totalNivel4, String totalNivel5, String totalNivel6,
                                  String totalNivel7, String totalNivel8, String descripTotal_nivel_1,
                                  String descripTotal_nivel_2, String descripTotal_nivel_3,
                                  String descripTotal_nivel_4, String descripTotal_nivel_5,
                                  String descripTotal_nivel_6, String descripTotal_nivel_7,
                                  String descripTotal_nivel_8, String ctaNombreNivel1, 
                                  String ctaNombreNivel2, String ctaNombreNivel3, String ctaNombreNivel4, 
                                  String ctaNombreNivel5, String ctaNombreNivel6, String ctaNombreNivel7, 
                                  String ctaNombreNivel8, Double saldoInicial, Double totalDebe,
                                  Double totalHaber, Double totalActivos, Double totalPasivoCapital,
                                  Double totalEgresoIngresos){
        this.ctaNivel = ctaNivel;
        this.cta = cta;
        this.ctaNOmbre   = ctaNOmbre;
        this.ctaNivel1   = ctaNivel1;
        this.ctaNivel2   = ctaNivel2;
        this.ctaNivel3   = ctaNivel3;
        this.ctaNivel4   = ctaNivel4;
        this.ctaNivel5   = ctaNivel5;
        this.ctaNivel6   = ctaNivel6;
        this.ctaNivel7   = ctaNivel7;
        this.ctaNivel8   = ctaNivel8;
        this.total       = total;
        this.totalNivel1 = totalNivel1;
        this.totalNivel2 = totalNivel2;
        this.totalNivel3 = totalNivel3;
        this.totalNivel4 = totalNivel4;
        this.totalNivel5 = totalNivel5;
        this.totalNivel6 = totalNivel6;
        this.totalNivel7 = totalNivel7;
        this.totalNivel8 = totalNivel8;
        this.descripTotal_nivel_1 = descripTotal_nivel_1;
        this.descripTotal_nivel_2 = descripTotal_nivel_2;
        this.descripTotal_nivel_3 = descripTotal_nivel_3;
        this.descripTotal_nivel_4 = descripTotal_nivel_4;
        this.descripTotal_nivel_5 = descripTotal_nivel_5;
        this.descripTotal_nivel_6 = descripTotal_nivel_6;
        this.descripTotal_nivel_7 = descripTotal_nivel_7;
        this.descripTotal_nivel_8 = descripTotal_nivel_8;
        this.ctaNombreNivel1 = ctaNombreNivel1;
        this.ctaNombreNivel2 = ctaNombreNivel2;
        this.ctaNombreNivel3 = ctaNombreNivel3;
        this.ctaNombreNivel4 = ctaNombreNivel4;
        this.ctaNombreNivel5 = ctaNombreNivel5;
        this.ctaNombreNivel6 = ctaNombreNivel6;
        this.ctaNombreNivel7 = ctaNombreNivel7;
        this.ctaNombreNivel8 = ctaNombreNivel8;
        this.saldoInicial = saldoInicial;
        this.totalDebe = totalDebe;
        this.totalHaber = totalHaber;
        
        this.totalActivos         = totalActivos;
        this.totalPasivoCapital   = totalPasivoCapital;
        this.totalEgresoIngresos  = totalEgresoIngresos;
    }

    public String getCta() {
        return cta;
    }

    public String getCtaNOmbre() {
        return ctaNOmbre;
    }

    public String getCtaNivel1() {
        return ctaNivel1;
    }

    public String getCtaNivel2() {
        return ctaNivel2;
    }

    public String getCtaNivel3() {
        return ctaNivel3;
    }

    public String getCtaNivel4() {
        return ctaNivel4;
    }

    public String getCtaNivel5() {
        return ctaNivel5;
    }

    public String getCtaNivel6() {
        return ctaNivel6;
    }

    public String getCtaNivel7() {
        return ctaNivel7;
    }

    public String getCtaNivel8() {
        return ctaNivel8;
    }

    public int getCtaNivel() {
        return ctaNivel;
    }

    public Double getTotal() {
        return total;
    }

    public String getTotalNivel1() {
        return totalNivel1;
    }

    public String getTotalNivel2() {
        return totalNivel2;
    }

    public String getTotalNivel3() {
        return totalNivel3;
    }

    public String getTotalNivel4() {
        return totalNivel4;
    }

    public String getTotalNivel5() {
        return totalNivel5;
    }

    public String getTotalNivel6() {
        return totalNivel6;
    }

    public String getTotalNivel7() {
        return totalNivel7;
    }

    public String getTotalNivel8() {
        return totalNivel8;
    }

    public String getDescripTotal_nivel_1() {
        return descripTotal_nivel_1;
    }

    public String getDescripTotal_nivel_2() {
        return descripTotal_nivel_2;
    }

    public String getDescripTotal_nivel_3() {
        return descripTotal_nivel_3;
    }

    public String getDescripTotal_nivel_4() {
        return descripTotal_nivel_4;
    }

    public String getDescripTotal_nivel_5() {
        return descripTotal_nivel_5;
    }

    public String getDescripTotal_nivel_6() {
        return descripTotal_nivel_6;
    }

    public String getDescripTotal_nivel_7() {
        return descripTotal_nivel_7;
    }

    public String getDescripTotal_nivel_8() {
        return descripTotal_nivel_8;
    }

    public String getCtaNombreNivel1() {
        return ctaNombreNivel1;
    }

    public String getCtaNombreNivel2() {
        return ctaNombreNivel2;
    }

    public String getCtaNombreNivel3() {
        return ctaNombreNivel3;
    }

    public String getCtaNombreNivel4() {
        return ctaNombreNivel4;
    }

    public String getCtaNombreNivel5() {
        return ctaNombreNivel5;
    }

    public String getCtaNombreNivel6() {
        return ctaNombreNivel6;
    }

    public String getCtaNombreNivel7() {
        return ctaNombreNivel7;
    }

    public String getCtaNombreNivel8() {
        return ctaNombreNivel8;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public Double getTotalDebe() {
        return totalDebe;
    }

    public Double getTotalHaber() {
        return totalHaber;
    }

    public Double getTotalActivos() {
        return totalActivos;
    }

    public Double getTotalPasivoCapital() {
        return totalPasivoCapital;
    }

    public Double getTotalEgresoIngresos() {
        return totalEgresoIngresos;
    }   
}