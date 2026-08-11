//
//  ContentView.swift
//  FoodPicker
//
//  Created by Chaiau on 2026/8/11.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            Image("images")
				.resizable()
				.aspectRatio(contentMode:.fit)
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
