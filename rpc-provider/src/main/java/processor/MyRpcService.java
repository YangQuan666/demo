package processor;

import annotation.RpcProvider;
import org.springframework.stereotype.Service;

@Service
@RpcProvider(interfaceName = MyRpcService.class, version = "1.2.3")
public class MyRpcService {
    private int x = 3;
}
