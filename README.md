# FindMyRoute

Work order organizing android application. After loggin in, you can add locations with marker and when you clicked marker, you can move it. You can look at details of the location but can't update it. After creating locations, you can make a optimized route starting from your location and finished at your location. If your GPS is not on, GPS turn on and permission request dialogs will show up. After turning GPS on, camera will be moved to the current location.
For GPS on/off listening BroadcastReceiver used.

Using:
Room,
Retrofit2,
Timber,
Gson,
Coroutine,
MVVM,
LiveData,
ViewModel,
Hilt,
BroadcastReceiver

API Services:
Google maps and OpenRouteService (used for creating optimized route)
