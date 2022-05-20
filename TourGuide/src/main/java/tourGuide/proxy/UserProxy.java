package tourGuide.proxy;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user", url = "localhost:8181")
public interface UserProxy {

}