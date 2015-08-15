# android-fast-dao

Framework for fast and easy rest and post webservice communication.


# Features
- Auto parse json based on objet type (lists and objects);
- Parse entire webservice response (objects, status, msg, etc)
- Auto add **progress dialogs**;
- Auto **save json for offline usage (with parsed objects and list)**;
- Auto add on error dialog (**try again dialog**);
- Auto inflate **loading and error layout with custom try again button**;
- Possibility to **create a dao** for every object **or** create a **single request** at runTime;
- Possibility to override a url for a single request only;
- **Post and Rest** support (you can do any type of request, but those come with examples by default);

# example

Open **JsonMessage.class** and adapt to your webservice return.


**Doing a single request:**
```java
new RestDao<MyModel>(Activity, "MyModelBaseUrlName", "serverUrl").getAll(CallListListener); 
```
**Creating a dao:**

Extends RestDao or PostDao using the model that you want :

```java
    public class TelephoneDao extends RestDao<Telephone> {
        public TelephoneDao(Activity activity) {
            super(activity);
            super.modelUrl = "telephone"; // this is the url under the domain for telephone object
            super.serverUrl = "http://myserver.com/";
        }
    }
```
    
It inerits four methods (getAll, getById, add, delete).

Then on your activities you must use a CallListener to handle the json parse, save local data, create dialogs, etc:
    
```java
           private void getData() {

            OnDialogButtonClick onDialogButtonClick = new OnDialogButtonClick() {
                @Override
                public void onPositiveClick() {
                    getData(); // what to do on try again click if connection fail, usually try again
                }

                @Override
                public void onNegativeClick() {
                    // what to do on close click if connection fail
                }
            };

            CallListListener<Telephone> callListener = new CallListListener<Telephone>(this, Telephone.class, "Message to show on dialog", onDialogButtonClick, true, null) {

                @Override
                public void onErrorResponse(VolleyError error) {
                    super.onErrorResponse(error);
                    list = this.getLocalList();   //  data downloaded before
                }

                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                        if (this.success()) { // check if things are fine
                            list = this.jsonMessageList.getObject(); // get the parsed list
                          } else { // request is ok, but your server returned an error or not found
                            Toast.makeText(this.activity.getApplicationContext(),
                                    this.getErrorMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                }
            };

            new TelephoneDao(getActivity()).getAll(callListener); // make the call
    }
```

The **true**, is for **saving** the parsed list **for offline usage** (it parses and saves the list using gson).

The **null** is to use the default class name for local save (you can pass a string here to save with custom name).

The **onDialogButtonClick** is optional, it creates an error dialog when the request fails.

The **"Message to show on dialog"** is optional, it creates a dialog while the request is beeing made.


The **getById request** use the same approach, but you **need to pass a CallSingleListener** (it parses a single object, instead of an array).

**You can create a single request with different url too:**
```
MyDao.getAll(callListListener, myURL);
MyDao.getById(callSingleListener, id, myUrl);
```

**There are more constructors for CallListener:**
- Auto inflate a layout with a progress dialog (to use with a relativeLayout parent);
