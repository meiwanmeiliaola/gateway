package sm.sm4;

/**
 * 赵项阳
 *
 * @author DELL
 * @date 2022/4/13 11:46
 */
public class SM4_Context {
    public int mode;

    public long[] sk;

    public boolean isPadding;

    public SM4_Context()
    {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new long[32];
    }
}
