package com.example.smarcityv1.CivicIssuesChatBot

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarcityv1.MentalHealthChatBot.ChatData
import com.example.smarcityv1.MentalHealthChatBot.ChatRoleEnum
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class CivicChatBotVM : ViewModel() {
    val list by lazy{
        mutableStateListOf<ChatData>()
    }

    private val genAI by lazy{
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "AIzaSyCWLrac4Mq9gdlXkztr3fDfEkwT4zMpE_I" // your gemini api key
        )
    }


    fun sendMessage(message : String )= viewModelScope.launch{

        val chat = genAI.startChat()

        list.add(ChatData(message,ChatRoleEnum.USER.role))

        chat.sendMessage(
            content(ChatRoleEnum.USER.role) { text(message) }
        ).text?.let{
            list.add(ChatData(it,ChatRoleEnum.MODEL.role))
        }
    }

}