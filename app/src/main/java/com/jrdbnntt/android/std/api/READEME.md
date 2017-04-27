Standard Android API Utilities
==============================

Project independent utilities for setting up easy to use API's based one Gson and Volley.

# Gradle Dependencies
```
dependencies {
    compile 'com.android.volley:volley:1.0.0'
    compile 'com.google.code.gson:gson:2.7'
}
```


# Recommended Directory Structure
* `api/`
    - `modules/`
        + `data/`
            * Response/Request GsonObjects
        + `YourModule.java` - ApiModule, preforms the requests using the data objects 
    - `types/`
        * Any types referenced across the app used in the api data, such as enum representations
    - `YourApi.java` - GsonVolleyApi, connects api users to modules