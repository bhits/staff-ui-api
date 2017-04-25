package gov.samhsa.c2s.staffuiapi.infrastructure;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("ums")
public interface UmsLookupClient extends UmsLookupService {
}
