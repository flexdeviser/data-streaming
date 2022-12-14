package org.e4s.stream;


import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.e4s.stream.imp.ScriptAction;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;

public class ScriptService {

    final Map<String, CompiledScript> scriptCache = new HashMap<>();


    /**
     * compile javascript
     *
     * @return
     */
    public CompiledScript compile(final String name, final File file, final boolean update) {

        if (scriptCache.containsKey(name) && !update) {
            return scriptCache.get(name);
        } else {
            Source src = Source.newBuilder("js", file).buildLiteral();
            Context ctx = Context.newBuilder().allowAllAccess(true).build();
            ctx.eval(src);
            ScriptAction action = new ScriptAction(ctx);
            scriptCache.put(name, action);
            return action;

        }

    }

}
