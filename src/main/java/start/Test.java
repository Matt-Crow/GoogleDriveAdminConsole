package start;


import java.io.IOException;
import java.security.GeneralSecurityException;
import structs.CertificationFormInfo;

// for crying out loud REMEMBER .EXECUTE()!!!
/**
 * 
 * https://cloud.google.com/compute/docs/api-rate-limits
 * https://console.developers.google.com/apis/api/drive.googleapis.com/quotas?authuser=2&project=camp-administration-console
 * 
 * @author Matt
 */

public class Test {
    public static void main(String... args) throws IOException, GeneralSecurityException {
        ServiceAccess service = ServiceAccess.getInstance();
        String accessListId = "1QHJvkVWmpgRZzY9bTeNSBSK_EDfMMHC8";
        
        CertificationFormInfo testCertFormInfo = new CertificationFormInfo(
            "1-eLPUcOp3MZyEo0g71hSt5P4-MD52kWh8FBomjVTVl4",
            "Form Responses 1",
            "Participants Name",
            "Participant's email",
            "Participant's Minecraft username",
            "Participating At What Level?"
        );
        
        CertificationFormInfo realCertFormInfo = new CertificationFormInfo(
            "1piKiPp3mqMVDsjEIZl5YJ2juDDFN8IL1esunD8okza0",
            "Form Responses 1",
            "Participants Name",
            "Participant's email",
            "Participant's Minecraft username ... Add To Science Report",
            "Participating At What Level?"
        );
        
        
        CertificationFormInfo june15 = new CertificationFormInfo(
            "1nF8Rbuc1Mnb0v_8ZB81Ci-Apge1bVQmMcA4AGHKsf4M",
            "Form Responses 1",
            "Participants Name",
            "Participant's email",
            "Participant's Minecraft username ... Add To Science Report",
            "Participating At What Level?"
        );
        
        
    }
}