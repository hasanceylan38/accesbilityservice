import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Accessibility Service',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const MethodChannel _channel =
      MethodChannel('com.example.flutter_application_21/accessibility');
 

  @override
  void initState() {
    super.initState();
   
  }

  Future<void> _startService() async {
    try {
      await _channel.invokeMethod('startService');
      print('Service Started');
    } on PlatformException catch (e) {
      print("Failed to start service: '${e.message}'.");
    }
  }

  Future<void> _stopService() async {
    try {
      await _channel.invokeMethod('stopService');
      print('Service Stopped');
    } on PlatformException catch (e) {
      print("Failed to stop service: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage("assets/resim.jpg"),
                fit: BoxFit.cover,
              ),
            ),
          ),
          Center(
            child: SingleChildScrollView(
              child: Center(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: <Widget>[
                    ElevatedButton(
                      onPressed: _startService,
                      child: Text('Start Service'),
                    ),
                    SizedBox(height: 20),
                    ElevatedButton(
                      onPressed: _stopService,
                      child: Text('Stop Service'),
                    ),
                    SizedBox(height: 20),
                  
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
