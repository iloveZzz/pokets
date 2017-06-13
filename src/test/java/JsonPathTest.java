import com.jayway.jsonpath.JsonPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhudaoming on 2017/6/12.
 */
public class JsonPathTest {
    public static void main(String[] args){
        String test_json="{\n" +
                "  \"push\": {\n" +
                "    \"changes\": [\n" +
                "      {\n" +
                "        \"forced\": false,\n" +
                "        \"old\": {\n" +
                "          \"type\": \"branch\",\n" +
                "          \"name\": \"master\",\n" +
                "          \"links\": {\n" +
                "            \"commits\": {\n" +
                "              \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commits/master\"\n" +
                "            },\n" +
                "            \"self\": {\n" +
                "              \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/refs/branches/master\"\n" +
                "            },\n" +
                "            \"html\": {\n" +
                "              \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/branch/master\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"target\": {\n" +
                "            \"hash\": \"b0089e9b4dca760bc179e88736ff8195ce862314\",\n" +
                "            \"links\": {\n" +
                "              \"self\": {\n" +
                "                \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "              },\n" +
                "              \"html\": {\n" +
                "                \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/commits/b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "              }\n" +
                "            },\n" +
                "            \"author\": {\n" +
                "              \"raw\": \"朱道明 <zhudaoming@xianbei.cc>\",\n" +
                "              \"type\": \"author\",\n" +
                "              \"user\": {\n" +
                "                \"username\": \"daomingzhu\",\n" +
                "                \"display_name\": \"zhudaoming\",\n" +
                "                \"type\": \"user\",\n" +
                "                \"uuid\": \"{03d9bd2d-76fd-4e0e-b642-a8725c8cc33a}\",\n" +
                "                \"links\": {\n" +
                "                  \"self\": {\n" +
                "                    \"href\": \"https://api.bitbucket.org/2.0/users/daomingzhu\"\n" +
                "                  },\n" +
                "                  \"html\": {\n" +
                "                    \"href\": \"https://bitbucket.org/daomingzhu/\"\n" +
                "                  },\n" +
                "                  \"avatar\": {\n" +
                "                    \"href\": \"https://bitbucket.org/account/daomingzhu/avatar/32/\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            \"parents\": [\n" +
                "              {\n" +
                "                \"type\": \"commit\",\n" +
                "                \"hash\": \"bf8f70322222ec3fb47d1307c613648de660aba1\",\n" +
                "                \"links\": {\n" +
                "                  \"self\": {\n" +
                "                    \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/bf8f70322222ec3fb47d1307c613648de660aba1\"\n" +
                "                  },\n" +
                "                  \"html\": {\n" +
                "                    \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/commits/bf8f70322222ec3fb47d1307c613648de660aba1\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            ],\n" +
                "            \"date\": \"2017-06-08T10:45:28+00:00\",\n" +
                "            \"message\": \"cccs\\n\",\n" +
                "            \"type\": \"commit\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"links\": {\n" +
                "          \"diff\": {\n" +
                "            \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/diff/181a91e8581b790fde86f3e74346fadf37aaadbf..b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "          },\n" +
                "          \"html\": {\n" +
                "            \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/branches/compare/181a91e8581b790fde86f3e74346fadf37aaadbf..b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "          },\n" +
                "          \"commits\": {\n" +
                "            \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commits?include=181a91e8581b790fde86f3e74346fadf37aaadbf&exclude=b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"created\": false,\n" +
                "        \"commits\": [\n" +
                "          {\n" +
                "            \"hash\": \"181a91e8581b790fde86f3e74346fadf37aaadbf\",\n" +
                "            \"links\": {\n" +
                "              \"self\": {\n" +
                "                \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/181a91e8581b790fde86f3e74346fadf37aaadbf\"\n" +
                "              },\n" +
                "              \"comments\": {\n" +
                "                \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/181a91e8581b790fde86f3e74346fadf37aaadbf/comments\"\n" +
                "              },\n" +
                "              \"patch\": {\n" +
                "                \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/patch/181a91e8581b790fde86f3e74346fadf37aaadbf\"\n" +
                "              },\n" +
                "              \"html\": {\n" +
                "                \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/commits/181a91e8581b790fde86f3e74346fadf37aaadbf\"\n" +
                "              },\n" +
                "              \"diff\": {\n" +
                "                \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/diff/181a91e8581b790fde86f3e74346fadf37aaadbf\"\n" +
                "              },\n" +
                "              \"approve\": {\n" +
                "                \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/181a91e8581b790fde86f3e74346fadf37aaadbf/approve\"\n" +
                "              },\n" +
                "              \"statuses\": {\n" +
                "                \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/181a91e8581b790fde86f3e74346fadf37aaadbf/statuses\"\n" +
                "              }\n" +
                "            },\n" +
                "            \"author\": {\n" +
                "              \"raw\": \"daomingzhu <596167028@qq.com>\",\n" +
                "              \"type\": \"author\",\n" +
                "              \"user\": {\n" +
                "                \"username\": \"zhudaoming\",\n" +
                "                \"display_name\": \"daoming zhu\",\n" +
                "                \"type\": \"user\",\n" +
                "                \"uuid\": \"{80efa0aa-f992-410e-8956-ef2ada5bcc7b}\",\n" +
                "                \"links\": {\n" +
                "                  \"self\": {\n" +
                "                    \"href\": \"https://api.bitbucket.org/2.0/users/zhudaoming\"\n" +
                "                  },\n" +
                "                  \"html\": {\n" +
                "                    \"href\": \"https://bitbucket.org/zhudaoming/\"\n" +
                "                  },\n" +
                "                  \"avatar\": {\n" +
                "                    \"href\": \"https://bitbucket.org/account/zhudaoming/avatar/32/\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            \"parents\": [\n" +
                "              {\n" +
                "                \"type\": \"commit\",\n" +
                "                \"hash\": \"b0089e9b4dca760bc179e88736ff8195ce862314\",\n" +
                "                \"links\": {\n" +
                "                  \"self\": {\n" +
                "                    \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "                  },\n" +
                "                  \"html\": {\n" +
                "                    \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/commits/b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            ],\n" +
                "            \"date\": \"2017-06-08T14:24:36+00:00\",\n" +
                "            \"message\": \"测试\\n\",\n" +
                "            \"type\": \"commit\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"truncated\": false,\n" +
                "        \"closed\": false,\n" +
                "        \"new\": {\n" +
                "          \"type\": \"branch\",\n" +
                "          \"name\": \"master\",\n" +
                "          \"links\": {\n" +
                "            \"commits\": {\n" +
                "              \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commits/master\"\n" +
                "            },\n" +
                "            \"self\": {\n" +
                "              \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/refs/branches/master\"\n" +
                "            },\n" +
                "            \"html\": {\n" +
                "              \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/branch/master\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"target\": {\n" +
                "            \"hash\": \"181a91e8581b790fde86f3e74346fadf37aaadbf\",\n" +
                "            \"links\": {\n" +
                "              \"self\": {\n" +
                "                \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/181a91e8581b790fde86f3e74346fadf37aaadbf\"\n" +
                "              },\n" +
                "              \"html\": {\n" +
                "                \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/commits/181a91e8581b790fde86f3e74346fadf37aaadbf\"\n" +
                "              }\n" +
                "            },\n" +
                "            \"author\": {\n" +
                "              \"raw\": \"daomingzhu <596167028@qq.com>\",\n" +
                "              \"type\": \"author\",\n" +
                "              \"user\": {\n" +
                "                \"username\": \"zhudaoming\",\n" +
                "                \"display_name\": \"daoming zhu\",\n" +
                "                \"type\": \"user\",\n" +
                "                \"uuid\": \"{80efa0aa-f992-410e-8956-ef2ada5bcc7b}\",\n" +
                "                \"links\": {\n" +
                "                  \"self\": {\n" +
                "                    \"href\": \"https://api.bitbucket.org/2.0/users/zhudaoming\"\n" +
                "                  },\n" +
                "                  \"html\": {\n" +
                "                    \"href\": \"https://bitbucket.org/zhudaoming/\"\n" +
                "                  },\n" +
                "                  \"avatar\": {\n" +
                "                    \"href\": \"https://bitbucket.org/account/zhudaoming/avatar/32/\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            \"parents\": [\n" +
                "              {\n" +
                "                \"type\": \"commit\",\n" +
                "                \"hash\": \"b0089e9b4dca760bc179e88736ff8195ce862314\",\n" +
                "                \"links\": {\n" +
                "                  \"self\": {\n" +
                "                    \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack/commit/b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "                  },\n" +
                "                  \"html\": {\n" +
                "                    \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/commits/b0089e9b4dca760bc179e88736ff8195ce862314\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            ],\n" +
                "            \"date\": \"2017-06-08T14:24:36+00:00\",\n" +
                "            \"message\": \"测试\\n\",\n" +
                "            \"type\": \"commit\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"actor\": {\n" +
                "    \"username\": \"daomingzhu\",\n" +
                "    \"display_name\": \"zhudaoming\",\n" +
                "    \"type\": \"user\",\n" +
                "    \"uuid\": \"{03d9bd2d-76fd-4e0e-b642-a8725c8cc33a}\",\n" +
                "    \"links\": {\n" +
                "      \"self\": {\n" +
                "        \"href\": \"https://api.bitbucket.org/2.0/users/daomingzhu\"\n" +
                "      },\n" +
                "      \"html\": {\n" +
                "        \"href\": \"https://bitbucket.org/daomingzhu/\"\n" +
                "      },\n" +
                "      \"avatar\": {\n" +
                "        \"href\": \"https://bitbucket.org/account/daomingzhu/avatar/32/\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"repository\": {\n" +
                "    \"scm\": \"git\",\n" +
                "    \"website\": \"\",\n" +
                "    \"uuid\": \"{47a0ca19-7b03-4276-aa87-1b1562e37024}\",\n" +
                "    \"links\": {\n" +
                "      \"self\": {\n" +
                "        \"href\": \"https://api.bitbucket.org/2.0/repositories/daomingzhu/react_webpack\"\n" +
                "      },\n" +
                "      \"html\": {\n" +
                "        \"href\": \"https://bitbucket.org/daomingzhu/react_webpack\"\n" +
                "      },\n" +
                "      \"avatar\": {\n" +
                "        \"href\": \"https://bitbucket.org/daomingzhu/react_webpack/avatar/32/\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"full_name\": \"daomingzhu/react_webpack\",\n" +
                "    \"owner\": {\n" +
                "      \"username\": \"daomingzhu\",\n" +
                "      \"display_name\": \"zhudaoming\",\n" +
                "      \"type\": \"user\",\n" +
                "      \"uuid\": \"{03d9bd2d-76fd-4e0e-b642-a8725c8cc33a}\",\n" +
                "      \"links\": {\n" +
                "        \"self\": {\n" +
                "          \"href\": \"https://api.bitbucket.org/2.0/users/daomingzhu\"\n" +
                "        },\n" +
                "        \"html\": {\n" +
                "          \"href\": \"https://bitbucket.org/daomingzhu/\"\n" +
                "        },\n" +
                "        \"avatar\": {\n" +
                "          \"href\": \"https://bitbucket.org/account/daomingzhu/avatar/32/\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"type\": \"repository\",\n" +
                "    \"is_private\": true,\n" +
                "    \"name\": \"react_webpack\"\n" +
                "  }\n" +
                "}";


        String actor= JsonPath.read(test_json,"$.repository.name");
        String path_branch= JsonPath.read(test_json,"$.push.changes..new.name").toString();
        String message= JsonPath.read(test_json,"$.push.changes..new.target.message").toString();
        String compare_html= JsonPath.read(test_json,"$.push.changes..new.target.links.html.href").toString();
        System.out.println(actor);
        Map c=new HashMap();
        c.put("git_branch","master");
        System.out.println(c.containsValue(path_branch));
        System.out.println(path_branch);
        System.out.println(message);
        System.out.println(compare_html);
    }
}
