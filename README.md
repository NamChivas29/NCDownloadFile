# NCDownloadFile


## Gradle dependency

```
allprojects {
    repositories {
        ...
	maven { url 'https://jitpack.io' }
	}
}
```

```
implementation 'com.github.NamChivas29:NCDownloadFile:1.0.1
```
## Sử dụng

### Ví dụ:
```java
new NCDownloadFile(YourActivity.this, link_tải, dường_dẫn_lưu_file) {
            @Override
            protected void onProgress(long percent) {
                /To do...
            }

            @Override
            protected void onFinish(Boolean isSuccess, String message) {
            	//To do...
            }
        }.start();
```




