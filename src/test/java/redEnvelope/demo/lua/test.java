package redEnvelope.demo.lua;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * @author : Meredith
 * @date : 2019-08-17 23:25
 * @description :
 */
public class test {

    public static void main(String[] args){

        String luaPath = "lua/redis-test.lua";   //lua脚本文件所在路径
        Globals globals = JsePlatform.standardGlobals();
        //加载脚本文件login.lua，并编译
        globals.loadfile(luaPath).call();
        //获取无参函数hello
        LuaValue func = globals.get(LuaValue.valueOf("hello"));
        //执行hello方法
        func.call();
        //获取带参函数test
        LuaValue func1 = globals.get(LuaValue.valueOf("hello1"));
        //执行test方法,传入String类型的参数参数
        String data = func1.call(LuaValue.valueOf("I'am from Java!")).toString();
        //打印lua函数回传的数据
        System.out.println(data);


    }
}
