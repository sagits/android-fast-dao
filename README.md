# android-fast-rest-dao

Framework for fast and easy rest and webservice communication.


Features
- Auto add progress dialogs;
- Auto save json for offline usage (with parsed objects and list);
- Auto parse object and object lists;
- Auto add on error dialog (try again dialog);
- Auto inflate loading and error layout with custom try again button;

# example

Open your values.xml and change server_url to your server url.

- You can define your server requests types on GenericAbstractDao.class, but its already pre configured for restfull.

Create a class that extends GenericAbstractDao using your the model that your want :


    public class TelephoneDao extends GenericAbstractDao<Telephone> {

      public TelephoneDao(Activity activity) {
        super(activity);
        super.modelUrl = "telephone"; // this is the url under the domain for telephone object
      }
      
    }
    
    
    It inerits the four common methods from GenericAbstractDao (getAll, getById, add, delete).
    
    
    Then on your activities you must use a CallListener to handle the json parse, save local data, create dialogs, etc:
    
    
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
                    
                    // what to do on error, you dont need to implement
                    
                /* list = this.getLocalList();   // optional to remove error dialog and populate from data downloaded before
                  if (list != null) { 
                        errorDialog.dismiss();
                        setAdapter(); // your method to set adapter, or what you want
                    } */
                }

                @Override
                public void onResponse(JSONObject response) {
                    super.onResponse(response);
                    try {

                        if (this.success()) { // check if things are fine
                            list = this.jsonMessageList.getObject(); // get the parsed list and add it to the activity list
                            setAdapter(); // set your adapter
                        } else { // request is ok, but your server returned an error or not found
                            Toast.makeText(this.activity.getApplicationContext(),
                                    this.getErrorMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e1) {
                    }
                }
            };

            new TelephoneDao(getActivity()).getAll(callListener); // make the call


    }

