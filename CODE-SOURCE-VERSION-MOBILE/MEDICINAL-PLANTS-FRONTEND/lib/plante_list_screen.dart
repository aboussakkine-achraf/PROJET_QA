import 'dart:async'; // Add this import
import 'package:flutter/material.dart';
import 'api_service.dart';
import 'plante.dart';
import 'plante_card.dart';
import 'search_plante.dart';

class PlanteListScreen extends StatefulWidget {
  @override
  _PlanteListScreenState createState() => _PlanteListScreenState();
}

class _PlanteListScreenState extends State<PlanteListScreen> {
  List<Plante> _allPlantes = [];
  List<Plante> _filteredPlantes = [];
  bool _isLoading = true;
  String _searchQuery = '';
  final FocusNode _searchFocusNode = FocusNode();
  Timer? _debounce; // Add a Timer to manage debounce

  @override
  void initState() {
    super.initState();
    _fetchPlantes();
  }

  Future<void> _fetchPlantes() async {
    try {
      final plantes = await ApiService.fetchPlantes();
      setState(() {
        _allPlantes = plantes;
        _filteredPlantes = plantes;
        _isLoading = false;
      });
    } catch (error) {
      setState(() {
        _isLoading = false;
      });
    }
  }

  void _onSearchChanged(String query) {
    // Cancel any previous timer
    if (_debounce?.isActive ?? false) _debounce!.cancel();

    // Set a new timer
    _debounce = Timer(const Duration(milliseconds: 300), () {
      setState(() {
        _searchQuery = query;
        _filteredPlantes = _allPlantes.where((plante) {
          final nameMatches =
              plante.nom.toLowerCase().contains(query.toLowerCase());
          final regionMatches =
              plante.regionGeo?.toLowerCase().contains(query.toLowerCase()) ??
                  false;
          final propertiesMatches =
              plante.proprietes?.toLowerCase().contains(query.toLowerCase()) ??
                  false;
          final utilisationMatches =
              plante.utilisation?.toLowerCase().contains(query.toLowerCase()) ??
                  false;

          return nameMatches ||
              regionMatches ||
              propertiesMatches ||
              utilisationMatches; // Include all conditions
        }).toList();
      });
    });
  }

  @override
  void dispose() {
    _debounce?.cancel(); // Cancel the timer if the widget is disposed
    _searchFocusNode.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        _searchFocusNode.unfocus();
      },
      child: Scaffold(
        appBar: AppBar(
          title: Text('List of Plants'),
        ),
        body: _isLoading
            ? Center(child: CircularProgressIndicator())
            : SingleChildScrollView(
                child: Column(
                  children: [
                    SearchPlante(
                      onSearchChanged: _onSearchChanged,
                      focusNode: _searchFocusNode,
                    ),
                    // Main content area
                    GridView.builder(
                      shrinkWrap:
                          true, // To make it scrollable within the screen
                      physics: NeverScrollableScrollPhysics(),
                      gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                        crossAxisCount: 2,
                        childAspectRatio: 1.0,
                      ),
                      itemCount: _filteredPlantes.length,
                      itemBuilder: (context, index) {
                        final plante = _filteredPlantes[index];
                        return PlanteCard(plante: plante);
                      },
                    ),
                  ],
                ),
              ),
      ),
    );
  }
}
