package cu.edu.cujae.backend.core.dto;

public class MunicipalityDto {
    private int codMunicipality;
    private MunicipalityDto municipality;

    public MunicipalityDto() {
    }

    public MunicipalityDto(int codMunicipality, MunicipalityDto municipality) {
        this.codMunicipality = codMunicipality;
        this.municipality = municipality;
    }

    public int getCodMunicipality() {
        return codMunicipality;
    }

    public void setCodMunicipality(int codMunicipality) {
        this.codMunicipality = codMunicipality;
    }

    public MunicipalityDto getMunicipality() {
        return municipality;
    }

    public void setMunicipality(MunicipalityDto municipality) {
        this.municipality = municipality;
    }
}
