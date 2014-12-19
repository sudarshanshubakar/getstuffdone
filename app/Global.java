import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import play.Application;
import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public class Global extends GlobalSettings {
//    @Override
//    public <T extends EssentialFilter> Class<T>[] filters() {
//        return new Class[]{CSRFFilter.class};
//    }
    
    private class ActionWrapper extends Action.Simple {
        public ActionWrapper(Action<?> action) {
            this.delegate = action;
        }

        @Override
        public Promise<Result> call(Http.Context ctx) throws java.lang.Throwable {
            Promise<Result> result = this.delegate.call(ctx);
            Http.Response response = ctx.response();
            response.setHeader(play.mvc.Http.HeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
//            response.setContentType("application/json");
            return result;
        }
    }

    @Override
    public Action<?> onRequest(Http.Request request, java.lang.reflect.Method actionMethod) {
    	System.out.println("On request filter");
        return new ActionWrapper(super.onRequest(request, actionMethod));
    }
    
    
    private Injector injector;
    
    @Override
    public void onStart(Application application) {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
//                bind(GreetingService.class).to(RealGreetingService.class);
            }
        });
    }
 
    @Override
    public <T> T getControllerInstance(Class<T> aClass) throws Exception {
        return injector.getInstance(aClass);
    }
}
