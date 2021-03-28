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

## How to use

### Example:

```
new NCDownloadFile(MainActivity.this, url, path) {
            @Override
            protected void onProgress(long percent) {
                progressDialog.setIndeterminate(false);
                progressDialog.setProgress((int) percent);
            }

            @Override
            protected void onFinsh(Boolean isSuccess, String message) {

                progressDialog.dismiss();
                if (isSuccess){
                    Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            }
        }.start();

```




