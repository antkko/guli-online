import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther macro
 * @description
 * @date 2023/12/08 19:31
 *     <p>/** * 编写一个 java 函数，实现获取 1000 个用户昵称的能力，具体要求如下： * 1）提供一个函数 Service.get(List<Long>
 *     userIds)，支持传入 1000 个用户 ID，返回用 * 户 ID 及其对应的昵称 * 2）在 Service.get 内部，通过调用
 *     UserService.getUserMap(List<Long> userIds)获取 * 数据，但 UserService.getUserMap 每次最多只接收 50 个用户
 *     ID，且该接口一次请求耗时 * 100ms * 3）Service.get(List<Long> userIds)函数需要将所有 1000 个传入的用户 ID 一次返回， *
 *     并且耗时需要保证在 200ms 以内。
 */
public class Service {
    public static Map<Long, String> get(final List<Long> userIds) {
        final Integer count = userIds.size();
        final Double page = count / 50.0;
        final Map<Long, String> result = new HashMap();
        for (int i = 0; i < page; i++) {
            final List<Long> subList =
                    userIds.stream().skip(i * 50).limit(50).collect(Collectors.toList());
            try {
                result.putAll(UserService.getUserMap(subList));
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(final String[] args) {
        // 帮我生成一个测试方案
        final List<Long> userIds = new ArrayList<>();
        for (int i = 0; i < 1001; i++) {
            userIds.add((long) (Math.random() * 100000000000000000L));
        }
        final Map<Long, String> longStringMap = get(userIds);
        System.out.println(longStringMap);
    }
}

class UserService {
    public static Map<Long, String> getUserMap(final List<Long> userIds)
            throws InterruptedException {
        if (userIds == null || userIds.size() > 50) {
            throw new RuntimeException("userids more than 50");
        }
        final Map<Long, String> result = new HashMap();
        for (final Long userId : userIds) {
            result.put(userId, "test");
        }
        Thread.sleep(100);
        return result;
    }
}
