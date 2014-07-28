package jadx.tests.internal.annotations;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static jadx.tests.utils.JadxMatchers.containsOne;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TestAnnotations extends InternalJadxTest {

	public static class TestCls {
		private static @interface A {
			int a();
		}

		@A(a = -1)
		public void methodA1() {
		}

		@A(a = -253)
		public void methodA2() {
		}

		@A(a = -11253)
		public void methodA3() {
		}

		private static @interface V {
			boolean value();
		}

		@V(false)
		public void methodV() {
		}

		private static @interface D {
			float value() default 1.1f;
		}

		@D
		public void methodD() {
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();
		System.out.println(code);

		assertThat(code, not(containsString("@A(a = 255)")));
		assertThat(code, containsOne("@A(a = -1)"));
		assertThat(code, containsOne("@A(a = -253)"));
		assertThat(code, containsOne("@A(a = -11253)"));
		assertThat(code, containsOne("@V(false)"));
		assertThat(code, not(containsString("@D()")));
		assertThat(code, containsOne("@D"));

		assertThat(code, containsOne("int a();"));
		assertThat(code, containsOne("float value() default 1.1f;"));
	}
}