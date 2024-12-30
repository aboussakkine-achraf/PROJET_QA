import 'package:flutter/material.dart';
import 'package:google_generative_ai/google_generative_ai.dart';

class AiScreen extends StatefulWidget {
  @override
  _AiScreenState createState() => _AiScreenState();
}

class _AiScreenState extends State<AiScreen> {
  final TextEditingController _controller = TextEditingController();
  final List<Map<String, String>> _messages = []; // Stores sender and message
  final ScrollController _scrollController = ScrollController();

  final String apiKey =
      'AIzaSyAIMcyXIbzhc_XvQ-E0e_F1Djcuu1EIcPI'; // Your API Key
  late final GenerativeModel _model;

  @override
  void initState() {
    super.initState();

    _model = GenerativeModel(
      model: 'gemini-1.5-flash',
      apiKey: apiKey,
      generationConfig: GenerationConfig(
        temperature: 1,
        topK: 40,
        topP: 0.95,
        maxOutputTokens: 8192,
        responseMimeType: 'text/plain',
      ),
      systemInstruction: Content.system('''
You are a medicinal plant expert . Your goal is to assist users by providing **short and concise answers** to their questions about medicinal plants. You have access to the following information for each plant:

1. **Name** (`nom`)  
2. **Description** (`description`)  
3. **Medicinal Properties** (`proprietes`)  
4. **How to Use the Plant** (`utilisation`)  
5. **Precautions** (`precautions`)  
6. **Interactions with Medications** (`interactions`)  
7. **Geographical Region** (`regionGeo`)  
8. **Article Link for Further Reading** (`articleLink`)  

### Guidelines:
1. **Be Brief and Relevant:** Provide only the most important details from the database to avoid overwhelming the user.
2. **Direct Answers:** Tailor your response to the question asked by the user. If the user asks about a specific plant, highlight its key uses, properties, and precautions.
3. **Encourage Further Learning:** Include the article link for users who want more detailed information.
4. **Safety First:** Always mention precautions or interactions if they are relevant to the user’s query.
5. **Friendly Tone:** Keep your responses conversational and helpful.

**Structure your responses like this:**
- Provide a quick summary of the plant or recommendation.
- Mention its most relevant properties or uses.
- Include a brief note on precautions, if applicable.
- Offer an article link for more information.

1. Specific Plant Inquiry

User: "Tell me about Chamomile."
Chatbot:
plaintext

Chamomile is a calming plant often used to improve sleep, reduce anxiety, and relieve digestive issues like indigestion and bloating. It also soothes skin irritations and promotes healing.

**Precautions:** Avoid if you’re allergic to plants in the Asteraceae family or taking blood thinners.  

Learn more: [Chamomile on Wikipedia](https://en.wikipedia.org/wiki/Chamomile)

2. Recommendation Based on Symptoms

User: "I have a headache. What can I use?"
Chatbot:
plaintext

For headaches, try:

- **Peppermint:** Its menthol helps relieve headaches and muscle pain.
- **Lavender:** Known for its relaxing effects, it can ease tension headaches.

**Precautions:** Peppermint oil should be diluted before use.  

Learn more: [Peppermint on Wikipedia](https://en.wikipedia.org/wiki/Peppermint)

3. Precaution Inquiry

User: "Are there any risks with using Ginger?"
Chatbot:
plaintext

Ginger is generally safe but can cause heartburn or irritation in large amounts. Avoid if you have a bleeding disorder or are on blood thinners.

Learn more: [Ginger on Wikipedia](https://en.wikipedia.org/wiki/Ginger)

4. General Educational Question

User: "What plant is good for boosting immunity?"
Chatbot:
plaintext

Echinacea is great for boosting immunity and reducing the severity of colds and flu. It’s often consumed as tea or capsules.

**Precautions:** Avoid long-term use if you have an autoimmune condition.

Learn more: [Echinacea on Wikipedia](https://en.wikipedia.org/wiki/Echinacea)

5. Geographical Information

User: "Where does Aloe Vera grow?"
Chatbot:
plaintext

Aloe Vera grows in tropical regions but is widely cultivated worldwide.

Learn more: [Aloe Vera on NCCIH](https://www.nccih.nih.gov/health/aloe-vera)

'''),
    );
  }

  void _sendMessage() async {
    final message = _controller.text.trim();
    if (message.isEmpty) return;

    setState(() {
      _messages.add({'sender': 'You', 'text': message});
    });

    _scrollToBottom();

    // AI Response
    final content = Content.text(message);
    final response = await _model.startChat().sendMessage(content);

    setState(() {
      _messages.add({
        'sender': 'YOU MEDICINAL PLANT ASSISTANT',
        'text': response.text ?? 'No response received.'
      });
    });

    _controller.clear();
    _scrollToBottom();
  }

  void _scrollToBottom() {
    Future.delayed(Duration(milliseconds: 100), () {
      if (_scrollController.hasClients) {
        _scrollController.animateTo(
          _scrollController.position.maxScrollExtent,
          duration: Duration(milliseconds: 300),
          curve: Curves.easeOut,
        );
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Medicinal Plant Chatbot'),
        backgroundColor: const Color(0xFF5C5470),
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              controller: _scrollController,
              itemCount: _messages.length,
              itemBuilder: (context, index) {
                final message = _messages[index];
                final isUser = message['sender'] == 'You';

                return Align(
                  alignment:
                      isUser ? Alignment.centerRight : Alignment.centerLeft,
                  child: Container(
                    margin: EdgeInsets.symmetric(vertical: 5, horizontal: 10),
                    padding: EdgeInsets.symmetric(vertical: 10, horizontal: 15),
                    decoration: BoxDecoration(
                      color: isUser ? Colors.blue[200] : Colors.green[200],
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Text(
                      '${message['sender']}: ${message['text']}',
                      style: TextStyle(fontSize: 16),
                    ),
                  ),
                );
              },
            ),
          ),
          Divider(height: 1, color: Colors.grey[300]),
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
                Expanded(
                  child: TextField(
                    controller: _controller,
                    decoration: InputDecoration(
                      hintText: 'Ask about medicinal plants...',
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(10),
                      ),
                    ),
                  ),
                ),
                SizedBox(width: 8),
                IconButton(
                  icon: Icon(Icons.send, color: Colors.green[700]),
                  onPressed: _sendMessage,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
