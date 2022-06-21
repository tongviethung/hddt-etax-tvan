package vn.teca.hddt.etax.tvan.model.enums;

public enum ErrorCode {
    SUCCESS("0", "Thành công"),
    // TODO: warning
    ERROR_SERVICE("-1", "Lỗi chưa xác định"),
    ERROR_UNSUPPORTED("-2", "Chưa hỗ trợ"),
    ERROR_DATABASE("-3", "Lỗi kết nối cơ sở dữ liệu"),
    ERROR_CACHE("-4", "Lỗi kiểm tra trùng bộ key"),

    // Common error
    ERR_80000("80000", "Sai định dạng dữ liệu"),
    ERR_80001("80001", "Trùng tờ khai dữ liệu hóa đơn"),
    ERR_80003("80003", "Lỗi bóc tách thông tin header"),
    ;

    private final String key;

    private final String value;

    ErrorCode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}